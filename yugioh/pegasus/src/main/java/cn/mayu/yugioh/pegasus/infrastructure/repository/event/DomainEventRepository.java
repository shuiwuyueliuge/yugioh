package cn.mayu.yugioh.pegasus.infrastructure.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainEventRepository extends JpaRepository<EventDO, Long> {
}
