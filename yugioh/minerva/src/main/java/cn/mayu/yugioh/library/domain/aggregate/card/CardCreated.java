package cn.mayu.yugioh.library.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class CardCreated implements DomainEvent {

    private CardIdentity cardIdentity;

    private String name;

    private String desc;

    private String typeVal;

    private String link;

    private String def;

    private String pend;

    private String race;

    private String attribute;

    private String level;

    private String atk;

    private List<String> typeSt;

    private List<String> linkArrow;

    private String eventType;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}
