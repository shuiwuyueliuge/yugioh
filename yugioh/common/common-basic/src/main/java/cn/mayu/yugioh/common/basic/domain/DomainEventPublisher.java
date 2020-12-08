package cn.mayu.yugioh.common.basic.domain;

import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 事件总线
 */
public class DomainEventPublisher {

    private Map<String, List<DomainEventConsumer>> consumers;

    private EventStore eventStore;

    public DomainEventPublisher(EventStore eventStore, List<DomainEventConsumer> eventConsumers) {
        this.eventStore = eventStore;
        this.consumers = eventConsumers.stream()
                .filter(data -> data.getEventType() != null)
                .collect(Collectors.groupingBy(DomainEventConsumer::getEventType));
    }

    public void publishEvent(DomainEvent domainEvent) {
        eventStore.store(domainEvent);
        consumers.get(domainEvent.getType()).forEach(data -> data.subscribe(domainEvent));
    }
}
