package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncludeCenterTaskCreated implements DomainEvent {

    private DataCenterTaskIdentity dataCenterTaskIdentity;

    private String operateChannel;

    private String cardPassword;

    private String resource;

    private String parentTask;

    @Override
    public long occurredOn() {
        return dataCenterTaskIdentity.getStartTime();
    }
}
