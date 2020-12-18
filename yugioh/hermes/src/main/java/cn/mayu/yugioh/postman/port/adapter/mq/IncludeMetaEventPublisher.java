package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(IncludeMetaOutStream.class)
public class IncludeMetaEventPublisher implements EventPublisherStrategy {

    @Autowired
    private IncludeMetaOutStream includeMetaOutStream;

    public boolean publish(DomainEvent domainEvent) {
        return includeMetaOutStream.includeMetaOutput().send(MessageBuilder.withPayload(domainEvent).build(), 3000L);
    }

    @Override
    public String eventType() {
        return IncludeMetaOutStream.INCLUDE_META_OUTPUT;
    }
}