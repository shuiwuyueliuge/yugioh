package cn.mayu.yugioh.common.basic.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RemoteDomainEvent implements DomainEvent {

    private long occurredOn;

    private String type;

    private String payload;

    private String routingKey;

    public RemoteDomainEvent(long occurredOn, String type, String payload) {
        this.type = type;
        this.payload = payload;
        this.occurredOn = occurredOn;
    }

    @Override
    public long occurredOn() {
        return occurredOn;
    }
}
