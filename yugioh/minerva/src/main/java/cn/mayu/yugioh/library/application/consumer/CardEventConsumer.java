package cn.mayu.yugioh.library.application.consumer;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.library.application.card.CardDTO;
import cn.mayu.yugioh.library.domain.aggregate.card.CardCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardEventConsumer implements DomainEventSubscribe<CardCreated> {

    @Autowired
    private EventFacade eventFacade;

    @Override
    public void subscribe(CardCreated cardCreated) {
        CardDTO cardDTO = new CardDTO(
                cardCreated.getName(),
                cardCreated.getDesc(),
                cardCreated.getTypeVal(),
                cardCreated.getLink(),
                cardCreated.getDef(),
                cardCreated.getPend(),
                cardCreated.getRace(),
                cardCreated.getCardIdentity().getPassword(),
                cardCreated.getAttribute(),
                cardCreated.getLevel(),
                cardCreated.getAtk(),
                cardCreated.getTypeSt(),
                cardCreated.getLinkArrow()
        );

        RemoteDomainEvent domainEvent = new RemoteDomainEvent(
                cardCreated.occurredOn(),
                cardCreated.getEventType(),
                JsonConstructor.defaultInstance().writeValueAsString(cardDTO),
                cardDTO.getPassword()
        );

        eventFacade.receiveEvent(new EventReceiveCommand(domainEvent));
    }

    @Override
    public Class<CardCreated> domainEventClass() {
        return CardCreated.class;
    }
}
