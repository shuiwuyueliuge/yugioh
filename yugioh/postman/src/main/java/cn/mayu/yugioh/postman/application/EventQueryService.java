package cn.mayu.yugioh.postman.application;

import cn.mayu.yugioh.postman.domain.aggregate.EventSourcing;
import cn.mayu.yugioh.postman.domain.aggregate.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventQueryService {

    @Autowired
    private EventSourcingRepository eventSourcingRepository;

    public List<EventSourcing> findByStatus(int status, int from, int size) {
        return eventSourcingRepository.findByStatus(status, from, size);
    }
}
