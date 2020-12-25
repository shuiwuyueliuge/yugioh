package cn.mayu.yugioh.library.domain.aggregate.include;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class IncludeCreated implements DomainEvent {

    private IncludeIdentity includeIdentity;

    private String packageName;

    private String packShortName;

    private String saleDate;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}
