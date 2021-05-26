package cn.mayu.yugioh.hermes.port.adapter.mq;

import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/5/17 5:51 下午
 */
//@Component
public class WebSocketPartitionExtractor implements PartitionKeyExtractorStrategy, PartitionSelectorStrategy {

    @Override
    public Object extractKey(Message<?> message) {
        return message.getPayload();
    }

    @Override
    public int selectPartition(Object key, int partitionCount) {
        return 0;
    }
}
