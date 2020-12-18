package cn.mayu.yugioh.aetos.domain;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CardIdentity extends ValueObject {

    // 卡片密码
    private String password;
}
