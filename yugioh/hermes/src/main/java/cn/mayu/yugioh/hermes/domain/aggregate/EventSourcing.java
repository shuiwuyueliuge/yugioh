package cn.mayu.yugioh.hermes.domain.aggregate;

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

    private final long eventId;

    private final long occurredOn;

    private final String payload;

    private final String type;

    private final String routingKey;

    private Integer status;

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

    public void publishCreatedEvent(EventSourcingService eventSourcingService) {
        eventSourcingService.publish(new EventSourcingCreated(
                eventId,
                occurredOn,
                payload,
                type,
                routingKey
        ));
    }
}
