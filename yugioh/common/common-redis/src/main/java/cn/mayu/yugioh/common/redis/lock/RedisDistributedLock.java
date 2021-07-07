package cn.mayu.yugioh.common.redis.lock;

import cn.mayu.yugioh.common.basic.tool.BeanManager;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class RedisDistributedLock implements Lock {

    private static final String LOCK_SCRIPT =
            "  if (redis.call('exists', KEYS[1]) == 0) then" +
                    "       redis.call('hincrby', KEYS[1], ARGV[2], 1);" +
                    "       redis.call('pexpire', KEYS[1], ARGV[1]);" +
                    "       return nil;" +
                    "  end;" +
                    "  if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then" +
                    "       redis.call('hincrby', KEYS[1], ARGV[2], 1);" +
                    "       redis.call('pexpire', KEYS[1], ARGV[1]);" +
                    "       return nil;" +
                    "  end;" +
                    "" +
                    "  return redis.call('pttl', KEYS[1]);";

    private static final String UNLOCK_SCRIPT =
            " if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then" +
                    "       return nil;" +
                    " end;" +
                    " local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1);" +
                    " if (counter > 0) then" +
                    "       redis.call('pexpire', KEYS[1], ARGV[2]);" +
                    "       return 0;" +
                    " else" +
                    "       redis.call('del', KEYS[1]);" +
                    "    redis.call('publish', KEYS[2], ARGV[1]);" + // KEYS[2]为redis消息队列的通道key ARGV[1]为消息值:0
                    "       return 1;" +
                    " end;" +
                    " return nil;";

    private final StringRedisTemplate redisTemplate;

    private final String lockKey;

    private static final String DEFAULT_TIMEOUT = "30000";

    private final String timeoutMillis;

    private final String uuId;

    private String currentThreadId;

    private ScheduledExecutorService executors;

    private Future<?> future;

    public RedisDistributedLock(String lockKey, Long timeoutMillis) {
        this.redisTemplate = BeanManager.getBean(StringRedisTemplate.class);
        this.lockKey = lockKey;
        this.uuId = UUID.randomUUID().toString();
        this.timeoutMillis = (timeoutMillis == null) ? DEFAULT_TIMEOUT : String.valueOf(timeoutMillis);
    }

    public RedisDistributedLock(String lockKey) {
        this(lockKey, null);
    }

    @Override
    public void lock() {
        Long ttl = tryAcquire();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        BeanManager.getBean(LockObservable.class).addObserver(String.format("%s%s", uuId, Thread.currentThread().getId()), new LockObserver(countDownLatch));
        while (!Objects.isNull(ttl)) { // 加锁失败
            try {
                countDownLatch.await(Long.parseLong(timeoutMillis), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                log.error("wait try lock", e);
            }

            ttl = tryAcquire();
        }

        BeanManager.getBean(LockObservable.class).removeObserver(String.format("%s%s", uuId, Thread.currentThread().getId()));
        // 获取锁成功后开启续命线程
        scheduleWatchdog();
    }

    private void scheduleWatchdog() {
        if (executors != null && future != null) {
            return;
        }

        executors = Executors.newScheduledThreadPool(1);
        long checkTime = Long.parseLong(timeoutMillis) / 3;
        TimeUnit unit = TimeUnit.MILLISECONDS;
        future = executors.scheduleAtFixedRate(() -> {
            Long lifeTtl = redisTemplate.getExpire(lockKey, unit);
            if (Objects.isNull(lifeTtl) || lifeTtl > checkTime) {
                return;
            }

            long amountAdd = checkTime + lifeTtl;
            Boolean addResult = redisTemplate.expire(lockKey, amountAdd, unit);
            if (addResult == null || !addResult) { // 超时时间设置很小的时候会导致续命时候key超时，导致返回false
                log.error("Can't update lock " + lockKey + " expiration");
            }
        }, checkTime, checkTime, unit);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        lock();
    }

    @Override
    public boolean tryLock() {
        return Objects.isNull(tryAcquire());
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long waitTime = unit.toMillis(unit.convert(time, unit));
        long deadline = System.currentTimeMillis() + waitTime;
        while (deadline > System.currentTimeMillis()) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

            if (Objects.isNull(tryAcquire())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 尝试获取锁 使用redis的hash结构存储，使用lua脚本代替setnx，保证了原子性。
     * 如果key-value不存在说明可以获取锁，并且重入计数为1。
     * 如果key-value存在说明是重入，重入计数++。
     * 以上情况都不是说明锁被占用返回锁的超时时间。
     *
     * @return key的超时时间 毫秒
     */
    private Long tryAcquire() {
        String threadId = String.format("%s%s", uuId, Thread.currentThread().getId());
        RedisScript<Long> redisScript = new DefaultRedisScript<>(LOCK_SCRIPT, Long.class);
        Long ttl = redisTemplate.execute(redisScript, new StringRedisSerializer(), getLongRedisSerializer(),
                Collections.singletonList(lockKey), timeoutMillis, threadId);
        if (Objects.isNull(ttl)) {
            this.currentThreadId = threadId;
        }

        return ttl;
    }

    private RedisSerializer<Long> getLongRedisSerializer() {
        return new RedisSerializer<Long>() {

            @Override
            public byte[] serialize(Long aLong) throws SerializationException {
                return (aLong + "").getBytes();
            }

            @Override
            public Long deserialize(byte[] bytes) throws SerializationException {
                return Long.parseLong(new String(bytes));
            }
        };
    }

    @Override
    public void unlock() {
        // 删除锁
        RedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
        Long res = redisTemplate.execute(redisScript, Lists.newArrayList(lockKey, "lock_wakeup"), "0", timeoutMillis, currentThreadId);
        if (1 == res) {
            // 停止续命线程,重入的情况
            future.cancel(Boolean.TRUE);
            executors.shutdownNow();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}