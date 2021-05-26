package cn.mayu.yugioh.minerva.domain.aggregate.packages;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.minerva.domain.aggregate.card.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class CardPackageCreated implements DomainEvent {

    private final CardPackageIdentity packageIdentity;

    private final String saleDate;

    private final List<Card> cards;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}
