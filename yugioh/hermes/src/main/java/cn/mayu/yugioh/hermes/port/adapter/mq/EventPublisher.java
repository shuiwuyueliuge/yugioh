package cn.mayu.yugioh.hermes.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcing;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcingCreated;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcingRepository;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcingService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class EventPublisher implements EventSourcingService {

    private final EventSourcingRepository eventSourcingRepository;

    private final StreamBridge bridge;

    public EventPublisher(EventSourcingRepository eventSourcingRepository,
                          StreamBridge bridge) {
        this.eventSourcingRepository = eventSourcingRepository;
        this.bridge = bridge;
    }

    public void publish(EventSourcingCreated eventSourcing) {
        Mono.just(eventSourcing)
                .publishOn(Schedulers.newParallel("publish eventSourcing to mq"))
                .subscribe(created -> {
                    RemoteDomainEvent domainEvent = new RemoteDomainEvent(
                            created.getOccurredOn(),
                            created.getType(),
                            created.getPayload(),
                            created.getRoutingKey()
                    );

                    boolean publishSuccess = bridge.send(domainEvent.getType(), domainEvent);
                    if (publishSuccess) {
                        EventSourcing sourcing = eventSourcingRepository.findById(created.getEventId());
                        sourcing.publishSuccess();
                        eventSourcingRepository.store(sourcing);
                        return;
                    }

                    // TODO 发布失败
                });
    }
}
