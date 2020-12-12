package cn.mayu.yugioh.library.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class Card extends Entity {

    // 唯一标识
    private CardIdentity cardIdentity;

    // 名称
    private Name name;

    // 怪兽信息
    private Monster monster;

    // 效果
    private Description description;

    // 图片地址
    private String imgUrl;

    // 卡片类型
    private List<String> typeSt;

    // wiki
    private String adjust;

    // 卡类型
    private String typeVal;

    public void commitTo() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new DomainEvent(
                SnowFlake.nextId(),
                System.currentTimeMillis(),
                "card",
                this,
                this,
                cardIdentity.getPassword()
        ));
    }
}
