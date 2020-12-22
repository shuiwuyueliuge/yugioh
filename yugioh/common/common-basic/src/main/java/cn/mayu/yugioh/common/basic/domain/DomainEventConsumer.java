package cn.mayu.yugioh.common.basic.domain;

public interface DomainEventConsumer<T> {

    void subscribe(DomainEvent<T> domainEvent);

    /**
     * 匹配是否拦截事件
     * @return 事件类型
     */
    boolean matchEvent(String type);
}
