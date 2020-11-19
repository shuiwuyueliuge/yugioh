package cn.mayu.yugioh.pegasus.domain.aggregate.cardlist;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.Getter;

@Getter
public class CardInfo extends ValueObject {

    private CardInfoIdentity cardInfoIdentity;

    private String cardImg;

    private String cardName;

    private String cardUrl;

    public CardInfo(String str) {

    }

    public void commitTo() {
    }
}
