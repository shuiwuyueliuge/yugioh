package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(EventOutStream.class)
public class EventPublisher {

    @Autowired
    private EventOutStream eventOutStream;

    public boolean publish(DomainEvent domainEvent) {
        return eventOutStream.eventOutput().send(MessageBuilder.withPayload(domainEvent).build(), 3000L);
    }
}