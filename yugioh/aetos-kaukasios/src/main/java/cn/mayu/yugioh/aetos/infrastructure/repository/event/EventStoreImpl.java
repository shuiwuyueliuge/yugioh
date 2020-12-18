package cn.mayu.yugioh.aetos.infrastructure.repository.event;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventStoreImpl implements EventStore {

    @Autowired
    private JpaEventRepository domainEventRepository;

    @Override
    public void store(DomainEvent domainEvent) {
        domainEventRepository.save(new EventDO(
                domainEvent.getEventId(),
                domainEvent.getOccurredOn(),
                domainEvent.getType(),
                JsonConstructor.defaultInstance().writeValueAsString(domainEvent.getBody()))
        );
    }
}
