package cn.mayu.yugioh.common.redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import java.util.function.Function;

/**
 * @description: redis操作
 * @author: YgoPlayer
 * @time: 2021/6/23 5:18 下午
 */
public class RedisOperator {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisOperator(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public Long lPush(byte[] key, byte[]... value) {
        return operate(conn -> {
            conn.openPipeline();
            Long res = conn.lPush(key, value);
            conn.closePipeline();
            return res;
        });
    }

    public byte[] lPop(byte[] key) {
        return operate(conn -> conn.rPop(key));
    }

    private <T> T operate(Function<RedisConnection, T> function) {
        try (RedisConnection conn = redisConnectionFactory.getConnection()) {
            return function.apply(conn);
        }
    }
}
