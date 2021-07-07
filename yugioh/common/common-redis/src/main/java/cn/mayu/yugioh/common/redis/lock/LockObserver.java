package cn.mayu.yugioh.common.redis.lock;

import java.util.concurrent.CountDownLatch;

public class LockObserver {

    private final CountDownLatch countDownLatch;

    public LockObserver(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void wakeup(String wakeupSignal) {
        if ("0".equals(wakeupSignal)) {
            countDownLatch.countDown();
        }
    }
}
