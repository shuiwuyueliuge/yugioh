package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;

public interface EventPublisherStrategy {

    boolean publish(DomainEvent domainEvent);

    String eventType();
}
