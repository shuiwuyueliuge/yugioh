package cn.mayu.yugioh.common.agent.threadpool;

import cn.mayu.yugioh.common.agent.init.ThreadPoolInfo;
import net.bytebuddy.asm.Advice;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolBeforeExecuteAdvice {

    @Advice.OnMethodEnter
    public static void beforeExecute(
            @Advice.This Object obj,
            @Advice.Argument(0) Object abc
    ) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) obj;
        Thread t = (Thread) abc;
        String poolName = t.getName();
        if (t.getName().contains("-")) {
            poolName = poolName.substring(0, poolName.lastIndexOf("-"));
        }

        String msg = String.format("thread-pool-name:[%s], core-pool-size:[%s], maximum-pool-size:[%s], pool-size:[%s], largest-pool-size:[%s], active-count:[%s], completed-task-count:[%s], task-count:[%s]",
                poolName,
                executor.getCorePoolSize(),
                executor.getMaximumPoolSize(),
                executor.getPoolSize(),
                executor.getLargestPoolSize(),
                executor.getActiveCount(),
                executor.getCompletedTaskCount(),
                executor.getTaskCount()
        );

        ThreadPoolInfo poolInfo = new ThreadPoolInfo();
        poolInfo.setPoolName(poolName);
        poolInfo.setCorePoolSize(executor.getCorePoolSize());
        poolInfo.setMaximumPoolSize(executor.getMaximumPoolSize());
        poolInfo.setLargestPoolSize(executor.getLargestPoolSize());
        poolInfo.setActiveCount(executor.getActiveCount());
        poolInfo.setCompletedTaskCount(executor.getCompletedTaskCount());
        poolInfo.setTaskCount(executor.getTaskCount());
        poolInfo.add();
        //System.out.println(msg);
    }
}
