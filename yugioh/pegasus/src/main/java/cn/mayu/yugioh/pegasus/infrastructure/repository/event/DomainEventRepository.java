package cn.mayu.yugioh.pegasus.infrastructure.repository.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DomainEventRepository extends JpaRepository<EventDO, Long> {

    List<EventDO> findByStatus(int status, Pageable pageable);
}
