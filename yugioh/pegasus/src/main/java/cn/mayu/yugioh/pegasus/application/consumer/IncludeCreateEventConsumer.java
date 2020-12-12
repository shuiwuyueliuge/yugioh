package cn.mayu.yugioh.pegasus.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncludeCreateEventConsumer implements DomainEventConsumer {

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private DataCenterStrategy dataCenterStrategy;

    @Override
    public void subscribe(DomainEvent domainEvent) {
        List<IncludeDTO> includeList = dataCenterStrategy.findDataCenter((DataCenterEnum) domainEvent.getSource()).getIncludeData().data2IncludeDTO(domainEvent.getBody().toString());
        includeList.stream().forEach(includeDTO -> {
            DomainEvent<IncludeDTO> cardDTODomainEvent = new DomainEvent<>(
                    domainEvent.getEventId(),
                    domainEvent.getOccurredOn(),
                    domainEvent.getType(),
                    includeDTO,
                    domainEvent.getSource(),
                    includeDTO.getPassword()
            );

            eventFacade.receiveEvent(new EventReceiveCommand(cardDTODomainEvent));
        });
    }

    @Override
    public String getEventType() {
        return "include";
    }
}


