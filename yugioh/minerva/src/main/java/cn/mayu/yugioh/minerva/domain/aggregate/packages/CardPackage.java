package cn.mayu.yugioh.minerva.domain.aggregate.packages;

import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.minerva.domain.aggregate.card.CardIdentity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;
import java.util.Set;

/**
 * 卡包实体
 */
@Getter
@ToString
@AllArgsConstructor
public class CardPackage extends Entity {

    private final CardPackageIdentity packageIdentity;

    private final String saleDate;

    private final String volume;

    private Set<CardIdentity> cards;

    /**
     * 更想卡包的卡片列表
     *
     * @param cardsAdd 新增的卡片列表
     */
    public void updateCards(Set<CardIdentity> cardsAdd) {
        if (CollectionUtils.isEmpty(cards)) {
            cards = cardsAdd;
            return;
        }

        cards.addAll(cardsAdd);
    }
}
