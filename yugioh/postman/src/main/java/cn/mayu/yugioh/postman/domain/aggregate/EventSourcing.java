package cn.mayu.yugioh.postman.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class EventSourcing extends Entity {

    private String eventId;

    private String occurredOn;

    private String body;

    private String type;

    public EventSourcing(DomainEvent domainEvent) {
        this.eventId = domainEvent.getEventId();
        this.occurredOn = domainEvent.getOccurredOn();
        this.body = JsonConstructor.defaultInstance().writeValueAsString(domainEvent.getBody());
        this.type = domainEvent.getType();
    }
}
