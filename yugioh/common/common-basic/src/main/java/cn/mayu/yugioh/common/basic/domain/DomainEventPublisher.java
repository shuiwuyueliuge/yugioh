package cn.mayu.yugioh.common.basic.domain;

import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * 事件总线
 */
public class DomainEventPublisher {

    private List<DomainEventConsumer> consumers;

    private EventStore eventStore;

    public DomainEventPublisher(EventStore eventStore, List<DomainEventConsumer> eventConsumers) {
        this.eventStore = eventStore;
        this.consumers = eventConsumers;
    }

    public void publishEvent(DomainEvent domainEvent) {
        eventStore.store(domainEvent);
        if (consumers == null) {
            return;
        }

        consumers.stream().filter(consumer -> consumer.matchEvent(domainEvent.getType())).forEach(consumer -> consumer.subscribe(domainEvent));
    }

    public void register(DomainEventConsumer eventConsumer) {
        synchronized (DomainEventPublisher.class) {
            if (consumers == null) {
                consumers = Lists.newArrayList();
            }
        }

        consumers.add(eventConsumer);
    }
}
