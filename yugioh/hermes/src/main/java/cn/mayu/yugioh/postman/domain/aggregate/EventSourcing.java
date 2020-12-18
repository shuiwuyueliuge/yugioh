package cn.mayu.yugioh.postman.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class EventSourcing extends Entity {

    private long eventId;

    private long occurredOn;

    private String body;

    private String type;

    private String routingKey;

    private String source;

    private Integer status;

    public EventSourcing(DomainEvent domainEvent) {
        this.eventId = SnowFlake.nextId();
        this.occurredOn = domainEvent.getOccurredOn();
        this.body = JsonConstructor.defaultInstance().writeValueAsString(domainEvent.getBody());
        this.type = domainEvent.getType();
        this.routingKey = domainEvent.getRoutingKey();
        this.status = 0;
        this.source = domainEvent.getSource().toString();
    }

    public void publishSuccess() {
        this.status = 1;
    }
}
