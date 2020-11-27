package cn.mayu.yugioh.common.basic.domain;


/**
 * 发布本地领域事件
 */
public interface DomainEventPublisher {

    void publishEvent(DomainEvent domainEvent);
}
