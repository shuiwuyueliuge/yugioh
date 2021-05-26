package cn.mayu.yugioh.hermes.application;

import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcing;
import cn.mayu.yugioh.hermes.domain.aggregate.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventQueryService {

    @Autowired
    private EventSourcingRepository eventSourcingRepository;

    public List<EventSourcing> findByStatusOrderByOccurredOn(int status, int from, int size) {
        return eventSourcingRepository.findByStatusOrderByOccurredOn(status, from, size);
    }
}
