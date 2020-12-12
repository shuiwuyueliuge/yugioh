package cn.mayu.yugioh.postman.domain.aggregate;

import java.util.List;

public interface EventSourcingRepository {

    void store(EventSourcing eventSourcing);

    List<EventSourcing> findByStatusOrderByOccurredOn(int status, int from, int size);
}
