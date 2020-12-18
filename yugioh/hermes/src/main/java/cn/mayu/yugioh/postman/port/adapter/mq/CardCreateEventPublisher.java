package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(CardCreateOutStream.class)
public class CardCreateEventPublisher implements EventPublisherStrategy {

    @Autowired
    private CardCreateOutStream cardCreateOutStream;

    public boolean publish(DomainEvent domainEvent) {
        return cardCreateOutStream.cardCreateOutput().send(MessageBuilder.withPayload(domainEvent).build(), 3000L);
    }

    @Override
    public String eventType() {
        return CardCreateOutStream.CARD_CREATE_OUTPUT;
    }
}