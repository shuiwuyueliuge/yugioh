package cn.mayu.yugioh.pegasus.domain.aggregate.cardlist;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.Getter;

@Getter
public class CardInfo extends Entity {

    private CardInfoIdentity cardInfoIdentity;

    private String cardImg;

    private String cardName;

    private String cardUrl;

    public void commitTo() {
    }
}
