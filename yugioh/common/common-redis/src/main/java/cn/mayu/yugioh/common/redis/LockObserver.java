package cn.mayu.yugioh.common.redis;

import java.util.concurrent.CountDownLatch;

public class LockObserver {

    private CountDownLatch countDownLatch;

    public LockObserver(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void wakeup(String wakeupSignal) {
        if ("0".equals(wakeupSignal)) {
            countDownLatch.countDown();
        }
    }
}
