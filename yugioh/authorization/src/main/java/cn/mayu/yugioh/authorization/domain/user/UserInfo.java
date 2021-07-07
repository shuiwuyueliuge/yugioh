package cn.mayu.yugioh.authorization.domain.user;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserInfo extends Entity {

    private final UserInfoId userInfoId;

    private final String password;
}
