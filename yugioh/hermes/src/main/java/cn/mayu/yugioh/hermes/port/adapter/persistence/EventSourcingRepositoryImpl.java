package cn.mayu.yugioh.hermes.port.adapter.persistence;

import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcing;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventSourcingRepositoryImpl implements EventSourcingRepository {

    @Autowired
    private JpaEventSourcingRepository jpaEventSourcingRepository;

    @Override
    public void store(EventSourcing eventSourcing) {
        EventSourcingDO eventSourcingDO = new EventSourcingDO(
                eventSourcing.getEventId(),
                eventSourcing.getOccurredOn(),
                eventSourcing.getType(),
                eventSourcing.getPayload(),
                eventSourcing.getStatus(),
                eventSourcing.getRoutingKey()
        );

        EventSourcingDO saved = jpaEventSourcingRepository.findByEventId(eventSourcing.getEventId());
        if (saved != null) {
            eventSourcingDO.setId(saved.getId());
        }

        jpaEventSourcingRepository.save(eventSourcingDO);
    }

    @Override
    public List<EventSourcing> findByStatusOrderByOccurredOn(int status, int from, int size) {
        List<EventSourcingDO> eventList = jpaEventSourcingRepository.findByStatusOrderByOccurredOn(status, PageRequest.of(from, size));
        return eventList.stream().map(data -> new EventSourcing(
                data.getEventId(),
                data.getOccurredOn(),
                data.getPayload(),
                data.getType(),
                data.getRoutingKey(),
                data.getStatus()
        )).collect(Collectors.toList());
    }

    @Override
    public EventSourcing findById(long eventId) {
        EventSourcingDO sourcing = jpaEventSourcingRepository.findByEventId(eventId);
        return new EventSourcing(
                sourcing.getEventId(),
                sourcing.getOccurredOn(),
                sourcing.getPayload(),
                sourcing.getType(),
                sourcing.getRoutingKey(),
                sourcing.getStatus()
        );
    }
}
