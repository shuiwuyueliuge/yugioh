package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MetaDataCreated<T> implements DomainEvent {

    private MetaDataIdentity metaDataIdentity;

    private T data;

    private String operateChannelId;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}
