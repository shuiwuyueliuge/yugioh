package cn.mayu.yugioh.library.domain.aggregate.packages;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CardPackage extends Entity {

    private CardPackageIdentity packageIdentity;

    private String saleDate;

    public void commitTo(String channel) {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new DomainEvent(
                SnowFlake.nextId(),
                System.currentTimeMillis(),
                "package",
                this,
                channel,
                packageIdentity.getPackageName(),
                null
        ));
    }
}
