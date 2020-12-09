package cn.mayu.yugioh.postman.infrastructure.repository;

import cn.mayu.yugioh.postman.domain.aggregate.EventSourcing;
import cn.mayu.yugioh.postman.domain.aggregate.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventSourcingRepositoryImpl implements EventSourcingRepository {

    @Autowired
    private MysqlEventSourcingRepository mysqlEventSourcingRepository;

    @Override
    public void store(EventSourcing eventSourcing) {
        mysqlEventSourcingRepository.save(new EventSourcingDO(
                eventSourcing.getEventId(),
                eventSourcing.getOccurredOn(),
                eventSourcing.getType(),
                eventSourcing.getBody()
        ));
    }

    @Override
    public List<EventSourcing> findByStatus(int status, int from, int size) {
        List<EventSourcingDO> eventList = mysqlEventSourcingRepository.findByStatus(status, PageRequest.of(from, size));
        return eventList.stream().map(data -> new EventSourcing(
                data.getEventId(),
                data.getOccurredOn(),
                data.getBody(),
                data.getType()
        )).collect(Collectors.toList());
    }
}
