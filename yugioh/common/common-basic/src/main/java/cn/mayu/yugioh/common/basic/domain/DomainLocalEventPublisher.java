package cn.mayu.yugioh.common.basic.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DomainLocalEventPublisher implements DomainEventPublisher {

    private Map<String, List<DomainEventConsumer>> consumerMap;

    public DomainLocalEventPublisher(Set<DomainEventConsumer> consumers) {
        this.consumerMap = consumers.stream().filter(data -> data.getEventType() != null)
                .collect(Collectors.groupingBy(DomainEventConsumer::getEventType));
    }

    @Override
    public void publishEvent(DomainEvent domainEvent) {
        String type = domainEvent.getType();
        boolean exists = consumerMap.containsKey(type);
        if (!exists) {
            return;
        }

        consumerMap.get(type).stream().forEach(data -> data.subscribe(domainEvent));
    }
}
