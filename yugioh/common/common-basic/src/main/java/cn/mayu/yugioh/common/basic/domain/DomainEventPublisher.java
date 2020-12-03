package cn.mayu.yugioh.common.basic.domain;

import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;

public class DomainEventPublisher {

    private EventStore eventStore;

    public DomainEventPublisher(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void publishEvent(DomainEvent domainEvent) {
        eventStore.store(domainEvent);
    }
}
