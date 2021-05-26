package cn.mayu.yugioh.hermes.domain.aggregate;

import java.util.List;

public interface EventSourcingRepository {

    void store(EventSourcing eventSourcing);

    List<EventSourcing> findByStatusOrderByOccurredOn(int status, int from, int size);

    EventSourcing findById(long eventId);
}
