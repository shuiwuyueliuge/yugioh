package cn.mayu.yugioh.pegasus.domain.aggregate.cardinfo;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardInfo extends Entity {

    private CardInfoIdentity cardInfoIdentity;

    private String cardImg;

    private String cardName;

    private String cardUrl;

    public void commitTo() {
    }
}
