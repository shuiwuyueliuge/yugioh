package cn.mayu.yugioh.minerva.port.adapter.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;

/**
 * @description: rabbitmq 多线程接收数据
 * @author: YgoPlayer
 * @time: 2021/5/11 1:25 下午
 */
public abstract class RabbitMultithreadingReceiver<T> extends MultithreadingReceiver<T> {

    public RabbitMultithreadingReceiver(int workerSize, int queueSize, String threadNamePrefix) {
        super(workerSize, queueSize, threadNamePrefix);
    }

    @Override
    public void accept(Message<T> message) {
        super.accept(message);
        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        basicAck(channel, deliveryTag);
    }

    protected void basicAck(Channel channel, Long deliveryTag) { }
}
