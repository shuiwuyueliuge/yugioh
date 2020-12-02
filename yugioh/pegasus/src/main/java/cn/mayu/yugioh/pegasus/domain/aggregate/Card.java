package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class Card extends Entity {

    // 唯一标识
    private CardIdentity cardIdentity;

    // 名称
    private Name name;

    // 图片地址
    private String imgUrl;

    // 怪兽卡信息
    private Monster monster;

    // 描述
    private Description description;

    // 卡片类型
    private List<String> typeSt;

    // wiki
    private String adjust;

    // 卡类型(monster,magic,trap)
    private String typeVal;

    // 卡包/发售日/编号/罕贵/卡包缩写
    private List<Include> includes;

    /**
     * 发布领域事件
     */
    public void commitTo() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new DomainEvent(
                "createCardList",
                this
        ));
    }
}
