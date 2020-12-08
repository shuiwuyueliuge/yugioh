package cn.mayu.yugioh.postman.infrastructure.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MysqlEventSourcingRepository extends JpaRepository<EventSourcingDO, Long> {

    List<EventSourcingDO> findByStatus(int status, Pageable pageable);
}
