package cn.mayu.yugioh.pegasus.domain.aggregate.cardlist;

import cn.mayu.yugioh.common.basic.domain.ValueObject;

public class CardInfoIdentity extends ValueObject {

    private String identity;

    /**
     * @param dataCenter    数据中心
     */
    public CardInfoIdentity(String dataCenter, String name) {
        this.identity = String.format("%s_%s_%s", dataCenter, name, System.currentTimeMillis());
    }
}
