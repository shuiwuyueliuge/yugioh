package cn.mayu.yugioh.minerva.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.common.facade.minerva.model.CardCreateCommand;
import cn.mayu.yugioh.minerva.application.card.CardCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import java.util.Objects;

/**
 * @description: 接收卡片数据
 * @author: YgoPlayer
 * @time: 2021/5/11 10:11 上午
 * mq的问题：
 * 消息丢失 -> 消息持久化到mq，生产者确认投失败重复投递，消费者手动ack
 * 重复消费 -> 数据库数据直接覆盖
 * 消费顺序 -> 卡片消费不需要关注此问题
 * 多线程消费 -> 会出现消息顺序问题，同${消费顺序}
 */
@Slf4j
@Component
public class DataStormReceiver extends RabbitMultithreadingReceiver<RemoteDomainEvent> {

    private final CardCommandService cardCommandService;

    private static final String THREAD_NAME_PREFIX = "rabbitmq-dataStorm-async-thread-";

    public DataStormReceiver(DataStormReceiverThreadProperties properties,
                             CardCommandService cardCommandService) {
        super(properties.getWorkerSize(), properties.getQueueSize(), THREAD_NAME_PREFIX);
        this.cardCommandService = cardCommandService;
    }

    /**
     * 消费mq的卡片数据
     *
     * @param event 事件消息
     */
    @Override
    protected void doAccept(RemoteDomainEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("card data: {}", event);
        }

        CardCreateCommand cardCreateCommand =
                JsonParser.builder()
                        .failOnUnknownProperties(false)
                        .build()
                        .readObjectValue(event.getPayload(), CardCreateCommand.class);
        cardCommandService.createCard(cardCreateCommand);
    }

    /**
     * 手动ack
     *
     * @param channel channel
     * @param deliveryTag 唯一标识 ID，
     *                    当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，
     *                    RabbitMQ 会用 basic.deliver 方法向消费者推送消息，
     *                    这个方法携带了一个 delivery tag，
     *                    它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，
     *                    是一个单调递增的正整数，delivery tag 的范围仅限于 Channel。
     */
    @Override
    protected void basicAck(Channel channel, Long deliveryTag) {
        if (Objects.isNull(channel) || Objects.isNull(deliveryTag)) {
            return;
        }

        try {
            channel.basicAck(deliveryTag, Boolean.FALSE);
        } catch (Exception e) {
            log.error("DataStormReceiver basicAck error", e);
        }
    }
}
