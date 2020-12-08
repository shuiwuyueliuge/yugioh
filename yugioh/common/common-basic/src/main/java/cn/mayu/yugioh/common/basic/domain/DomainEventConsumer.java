package cn.mayu.yugioh.common.basic.domain;

public interface DomainEventConsumer<T> {

    void subscribe(DomainEvent<T> domainEvent);

    /**
     * 获取事件类型
     * @return 事件类型
     */
    String getEventType();
}
