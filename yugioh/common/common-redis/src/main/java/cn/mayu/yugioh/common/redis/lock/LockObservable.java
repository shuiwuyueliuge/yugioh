package cn.mayu.yugioh.common.redis.lock;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;

public class LockObservable {

    private static final Map<String, LockObserver> LOCK_OBSERVERS;

    static {
        LOCK_OBSERVERS = Maps.newConcurrentMap();
    }

    /**
     * redis的message通知
     *
     * @param wakeupSignal 0 解锁成功 | 1 | null
     */
    public void wakeup(String wakeupSignal) {
        if (Objects.isNull(wakeupSignal)) {
            return;
        }

        LOCK_OBSERVERS.values().forEach(observer -> observer.wakeup(wakeupSignal));
    }

    public void addObserver(String threadId, LockObserver observer) {
        LOCK_OBSERVERS.put(threadId, observer);
    }

    public void removeObserver(String threadId) {
        LOCK_OBSERVERS.remove(threadId);
    }
}