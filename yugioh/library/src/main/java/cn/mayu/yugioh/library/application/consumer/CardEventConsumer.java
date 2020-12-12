package cn.mayu.yugioh.library.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.library.domain.aggregate.card.Card;
import cn.mayu.yugioh.library.domain.aggregate.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardEventConsumer implements DomainEventConsumer<Card> {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void subscribe(DomainEvent<Card> domainEvent) {
        cardRepository.store(domainEvent.getBody());
    }

    @Override
    public String getEventType() {
        return "card";
    }
}
