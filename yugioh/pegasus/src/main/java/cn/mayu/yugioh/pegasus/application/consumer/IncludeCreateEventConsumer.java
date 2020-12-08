package cn.mayu.yugioh.pegasus.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncludeCreateEventConsumer implements DomainEventConsumer {

    @Autowired
    private EventFacade eventFacade;

    @Override
    public void subscribe(DomainEvent domainEvent) {
        eventFacade.receiveEvent(new EventReceiveCommand(domainEvent));
    }

    @Override
    public String getEventType() {
        return "include";
    }
}


