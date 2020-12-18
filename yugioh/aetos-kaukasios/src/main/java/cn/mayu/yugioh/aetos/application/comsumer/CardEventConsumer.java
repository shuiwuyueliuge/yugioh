package cn.mayu.yugioh.aetos.application.comsumer;

import cn.mayu.yugioh.aetos.domain.Card;
import cn.mayu.yugioh.aetos.domain.CardRepository;
import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
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
        return "card-index";
    }
}
