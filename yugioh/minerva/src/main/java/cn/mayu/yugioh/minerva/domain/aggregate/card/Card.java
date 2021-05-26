package cn.mayu.yugioh.minerva.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class Card extends Entity {

    // 唯一标识
    private final CardIdentity cardIdentity;

    // 名称
    private final Name name;

    // 怪兽信息
    private final Monster monster;

    // 效果
    private final Description description;

    // 图片地址
    private final String imgUrl;

    // 卡片类型
    private final List<String> typeSt;

    // 收录信息
    private final List<Include> include;

    // wiki
    private final String adjust;

    // 卡类型
    private final String typeVal;

    public void publishCardCreated() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new CardCreated(
                cardIdentity,
                name.getNameNw(),
                description.getDescNw(),
                typeVal,
                monster.getLink(),
                monster.getDef(),
                monster.getPend(),
                monster.getRace(),
                monster.getAttribute(),
                monster.getLevel(),
                monster.getAtk(),
                typeSt,
                monster.getLinkArrow(),
                "card-create",
                include
        ));
    }
}
