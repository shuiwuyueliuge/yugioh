package cn.mayu.yugioh.library.infrastructure.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEventRepository extends JpaRepository<EventDO, Long> {
}
