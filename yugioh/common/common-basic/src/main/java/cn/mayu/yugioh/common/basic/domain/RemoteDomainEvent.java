package cn.mayu.yugioh.common.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteDomainEvent implements DomainEvent {

    private long occurredOn;

    private String type;

    private String payload;

    private String routingKey;

    private String channel;

    public RemoteDomainEvent(long occurredOn, String type, String payload, String routingKey) {
        this.occurredOn = occurredOn;
        this.type = type;
        this.payload = payload;
        this.routingKey = routingKey;
    }

    @Override
    public long occurredOn() {
        return occurredOn;
    }
}
