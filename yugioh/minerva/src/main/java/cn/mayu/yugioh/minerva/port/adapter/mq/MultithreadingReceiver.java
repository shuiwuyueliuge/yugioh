package cn.mayu.yugioh.minerva.port.adapter.mq;

import lombok.Data;
import org.springframework.messaging.Message;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @description: 多线程消费mq数据基础类
 * @author: YgoPlayer
 * @time: 2021/5/11 10:11 上午
 *
 * @param <T> payload
 */
@Data
public abstract class MultithreadingReceiver<T> implements Consumer<Message<T>> {

    private int workerSize;

    private int queueSize;

    private static final long kEEP_ALIVE_TIME = 0L;

    private final ThreadPoolExecutor executor;

    public MultithreadingReceiver(int workerSize, int queueSize, String threadNamePrefix) {
        this.executor = new ThreadPoolExecutor(
                workerSize,
                workerSize,
                kEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize),
                genThreadFactory(threadNamePrefix),
                genExecutionHandler()
        );
    }

    @Override
    public void accept(Message<T> message) {
        T payload = message.getPayload();
        executor.execute(() -> doAccept(payload));
    }

    protected abstract void doAccept(T payload);

    private ThreadFactory genThreadFactory(String threadNamePrefix) {
        return r -> {
            Thread thread = new Thread(r);
            thread.setName(threadNamePrefix);
            return thread;
        };
    }

    protected RejectedExecutionHandler genExecutionHandler() {
        return new ThreadPoolExecutor.AbortPolicy();
    }
}
