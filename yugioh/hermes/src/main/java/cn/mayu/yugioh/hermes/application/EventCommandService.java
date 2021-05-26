package cn.mayu.yugioh.hermes.application;

import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcing;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcingRepository;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcingService;
import org.springframework.stereotype.Service;

@Service
public class EventCommandService {

    private final EventSourcingRepository eventSourcingRepository;

    private final EventSourcingService eventSourcingService;

    public EventCommandService(EventSourcingRepository eventSourcingRepository,
                               EventSourcingService eventSourcingService) {
        this.eventSourcingRepository = eventSourcingRepository;
        this.eventSourcingService = eventSourcingService;
    }

    public void receiveEvent(EventReceiveCommand eventReceiveCommand) {
        EventSourcing eventSourcing = new EventSourcing(eventReceiveCommand.getDomainEvent());
        eventSourcingRepository.store(eventSourcing);
        eventSourcing.publishCreatedEvent(eventSourcingService);
    }
}
