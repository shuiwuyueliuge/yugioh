package cn.mayu.yugioh.minerva.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CardIdentity extends ValueObject {

    // 卡片密码
    private final String password;
}
