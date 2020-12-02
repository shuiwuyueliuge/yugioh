package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
public class CardIdentity extends ValueObject {

    // 卡片密码
    private String password;

    public CardIdentity(String password) {
        if (StringUtils.isEmpty(password)) {
            this.password = SnowFlake.nextId() + "";
        }

        this.password = password;
    }
}
