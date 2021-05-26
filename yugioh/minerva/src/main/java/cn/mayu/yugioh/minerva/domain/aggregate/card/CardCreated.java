package cn.mayu.yugioh.minerva.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class CardCreated implements DomainEvent {

    private final CardIdentity cardIdentity;

    private final String name;

    private final String desc;

    private final String typeVal;

    private final String link;

    private final String def;

    private final String pend;

    private final String race;

    private final String attribute;

    private final String level;

    private final String atk;

    private final List<String> typeSt;

    private final List<String> linkArrow;

    private final String eventType;

    private final List<Include> include;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}
