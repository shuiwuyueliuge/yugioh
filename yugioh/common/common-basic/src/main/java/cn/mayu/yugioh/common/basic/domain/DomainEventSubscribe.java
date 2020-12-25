package cn.mayu.yugioh.common.basic.domain;

public interface DomainEventSubscribe<T extends DomainEvent> {

    void subscribe(T mainEvent);

    Class<T> domainEventClass();
}
