package cn.mayu.yugioh.common.basic.domain;

import java.util.List;

/**
 * 事件总线
 */
public class DomainEventPublisher {

    private final List<DomainEventSubscribe> subscribes;

    public DomainEventPublisher(List<DomainEventSubscribe> subscribes) {
        this.subscribes = subscribes;
    }

    public void publishEvent(DomainEvent domainEvent) {
        if (subscribes == null || subscribes.size() == 0) {
            return;
        }

        Class<?> eventClass = domainEvent.getClass();
        subscribes.stream().filter(consumer -> consumer.domainEventClass() == eventClass)
                .forEach(consumer -> consumer.subscribe(domainEvent));
    }
}
