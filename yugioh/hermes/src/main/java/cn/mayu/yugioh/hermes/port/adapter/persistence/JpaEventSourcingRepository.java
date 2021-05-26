package cn.mayu.yugioh.hermes.port.adapter.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaEventSourcingRepository extends JpaRepository<EventSourcingDO, Long> {

    List<EventSourcingDO> findByStatusOrderByOccurredOn(int status, Pageable pageable);

    EventSourcingDO findByEventId(Long eventId);
}
