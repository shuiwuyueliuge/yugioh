package cn.mayu.yugioh.common.agent.threadpool.test;

import java.util.concurrent.*;

public class AgentTest {

    public static void main(String[] args) {
        ExecutorService e = Executors.newFixedThreadPool(11);
        ThreadPoolExecutor t = (ThreadPoolExecutor) e;
        t.execute(() -> {});
        t.execute(() -> {});
        t.execute(() -> {});
        t.execute(() -> {});
        t.execute(() -> {});
        t.execute(() -> {});
        //t.shutdown();
    }
}
