package cn.mayu.yugioh.pegasus.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardCreateEventConsumer implements DomainEventConsumer {

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private DataCenterStrategy dataCenterStrategy;

    @Override
    public void subscribe(DomainEvent domainEvent) {
        CardDTO cardDTO = dataCenterStrategy.findDataCenter((DataCenterEnum) domainEvent.getSource()).getCardData().data2CardDTO(domainEvent.getBody().toString());
        DomainEvent<CardDTO> cardDTODomainEvent = new DomainEvent<>(
                domainEvent.getEventId(),
                domainEvent.getOccurredOn(),
                domainEvent.getType(),
                cardDTO,
                domainEvent.getSource()
        );

        eventFacade.receiveEvent(new EventReceiveCommand(cardDTODomainEvent));
    }

    @Override
    public String getEventType() {
        return "card";
    }
}


