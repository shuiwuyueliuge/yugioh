package cn.mayu.yugioh.postman.application;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.postman.domain.aggregate.EventSourcing;
import cn.mayu.yugioh.postman.domain.aggregate.EventSourcingRepository;
import cn.mayu.yugioh.postman.port.adapter.mq.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventCommandService {

    @Autowired
    private EventSourcingRepository eventSourcingRepository;

    @Autowired
    private EventPublisher eventPublisher;

    public void receiveEvent(EventReceiveCommand eventReceiveCommand) {
        EventSourcing eventSourcing = new EventSourcing(eventReceiveCommand.getDomainEvent());
        eventSourcingRepository.store(eventSourcing);
    }

    public void publishEvent(EventSourcing eventSourcing) {
        DomainEvent domainEvent = new DomainEvent(
                eventSourcing.getEventId(),
                eventSourcing.getOccurredOn(),
                eventSourcing.getType(),
                eventSourcing.getBody(),
                "",
                eventSourcing.getRoutingKey()
        );
        boolean publishSuccess = eventPublisher.publish(domainEvent);
        if (publishSuccess) {
            eventSourcing.publishSuccess();
            eventSourcingRepository.store(eventSourcing);
        }
    }
}
