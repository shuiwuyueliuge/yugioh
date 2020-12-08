package cn.mayu.yugioh.postman.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class EventSourcing extends Entity {

    private String id;

    private String occurredOn;

    private String body;

    private String type;

    public EventSourcing(DomainEvent domainEvent) {
        this.id = domainEvent.getEventId();
        this.occurredOn = domainEvent.getOccurredOn();
        this.body = domainEvent.getBody().toString();
        this.type = domainEvent.getType();
    }
}
