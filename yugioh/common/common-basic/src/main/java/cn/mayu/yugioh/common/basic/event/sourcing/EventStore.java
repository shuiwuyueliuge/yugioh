package cn.mayu.yugioh.common.basic.event.sourcing;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import java.util.List;

public interface EventStore {

    void store(DomainEvent domainEvent);

    List<DomainEvent> findByStatus(int status, int from, int size);
}
