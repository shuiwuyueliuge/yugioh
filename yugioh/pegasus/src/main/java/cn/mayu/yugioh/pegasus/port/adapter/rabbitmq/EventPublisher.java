package cn.mayu.yugioh.pegasus.port.adapter.rabbitmq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(EventOutStream.class)
public class EventPublisher {

    @Autowired
    private EventOutStream cardOutStream;

    public boolean publish(DomainEvent domainEvent) {
        return cardOutStream.eventOutput().send(MessageBuilder.withPayload(domainEvent).build(), 3000L);
    }
}