package cn.mayu.yugioh.common.basic.event.sourcing;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;

public interface EventStore {

    void store(DomainEvent domainEvent);
}
