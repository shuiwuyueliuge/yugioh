package cn.mayu.yugioh.postman.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
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

    private String payload;

    private String type;

    private String routingKey;

    private Integer status;

    private String channel;

    public EventSourcing(DomainEvent domainEvent) {
        RemoteDomainEvent remoteDomainEvent = (RemoteDomainEvent) domainEvent;
        this.eventId = SnowFlake.nextId();
        this.occurredOn = remoteDomainEvent.occurredOn();
        this.payload = remoteDomainEvent.getPayload();
        this.type = remoteDomainEvent.getType();
        this.routingKey = remoteDomainEvent.getRoutingKey();
        this.status = 0;
    }

    public void publishSuccess() {
        this.status = 1;
    }
}
