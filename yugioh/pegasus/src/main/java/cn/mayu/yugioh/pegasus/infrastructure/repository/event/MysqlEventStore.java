package cn.mayu.yugioh.pegasus.infrastructure.repository.event;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MysqlEventStore implements EventStore {

    @Autowired
    private DomainEventRepository domainEventRepository;

    @Override
    public void store(DomainEvent domainEvent) {
        domainEventRepository.save(new EventDO(
                domainEvent.getEventId(),
                domainEvent.getOccurredOn(),
                domainEvent.getType(),
                JsonConstructor.defaultInstance().writeValueAsString(domainEvent.getBody()))
        );
    }

    @Override
    public List<DomainEvent> findByStatus(int status, int from, int size) {
        List<EventDO> eventList = domainEventRepository.findByStatus(status, PageRequest.of(from, size));
        return eventList.stream().map(data -> new DomainEvent(
                data.getEventId(),
                data.getOccurredOn(),
                data.getType(),
                data.getBody()
        )).collect(Collectors.toList());
    }
}
