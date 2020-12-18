package cn.mayu.yugioh.library.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.library.application.card.CardDTO;
import cn.mayu.yugioh.library.domain.aggregate.card.Card;
import cn.mayu.yugioh.library.domain.aggregate.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardEventConsumer implements DomainEventConsumer<Card> {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EventFacade eventFacade;

    @Override
    public void subscribe(DomainEvent<Card> domainEvent) {
        cardRepository.store(domainEvent.getBody());
        Card card = domainEvent.getBody();
        CardDTO cardDTO = new CardDTO(
                card.getName().getName(),
                card.getDescription().getDesc(),
                card.getTypeVal(),
                card.getMonster().getLink(),
                card.getMonster().getDef(),
                card.getMonster().getPend(),
                card.getMonster().getRace(),
                card.getCardIdentity().getPassword(),
                card.getMonster().getAttribute(),
                card.getMonster().getLevel(),
                card.getMonster().getAtk(),
                card.getTypeSt(),
                card.getMonster().getLinkArrow()
        );

        DomainEvent<CardDTO> cardDTODomainEvent = new DomainEvent<>(
                domainEvent.getEventId(),
                domainEvent.getOccurredOn(),
                domainEvent.getType(),
                cardDTO,
                domainEvent.getSource(),
                cardDTO.getPassword(),
                null
        );

        eventFacade.receiveEvent(new EventReceiveCommand(cardDTODomainEvent));

        DomainEvent<CardDTO> msgDomainEvent = new DomainEvent<>(
                domainEvent.getEventId(),
                domainEvent.getOccurredOn(),
                "msg",
                cardDTO,
                domainEvent.getSource(),
                cardDTO.getPassword(),
                null
        );

        eventFacade.receiveEvent(new EventReceiveCommand(msgDomainEvent));
    }

    @Override
    public String getEventType() {
        return "card-create";
    }
}
