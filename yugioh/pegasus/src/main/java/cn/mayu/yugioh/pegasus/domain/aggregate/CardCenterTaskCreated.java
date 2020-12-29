package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardCenterTaskCreated implements DomainEvent {

    private DataCenterTaskIdentity dataCenterTaskIdentity;

    private String operateChannel;

    @Override
    public long occurredOn() {
        return dataCenterTaskIdentity.getStartTime();
    }
}
