package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(CardMetaOutStream.class)
public class CardMetaEventPublisher implements EventPublisherStrategy {

    @Autowired
    private CardMetaOutStream cardMetaOutStream;

    public boolean publish(DomainEvent domainEvent) {
        return cardMetaOutStream.cardMetaOutput().send(MessageBuilder.withPayload(domainEvent).build(), 3000L);
    }

    @Override
    public String eventType() {
        return CardMetaOutStream.CARD_META_OUTPUT;
    }
}