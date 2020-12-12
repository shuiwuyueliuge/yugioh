package cn.mayu.yugioh.library.domain.aggregate.include;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import cn.mayu.yugioh.library.domain.aggregate.card.CardIdentity;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackageIdentity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Include extends Entity {

    private IncludeIdentity includeIdentity;

    private CardPackageIdentity packageIdentity;

    private CardIdentity cardIdentity;

    private String serial;

    private String rare;

    private String includeTime;

    public void commitTo() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new DomainEvent(
                SnowFlake.nextId(),
                System.currentTimeMillis(),
                "include",
                this,
                this,
                cardIdentity.getPassword()
        ));
    }
}
