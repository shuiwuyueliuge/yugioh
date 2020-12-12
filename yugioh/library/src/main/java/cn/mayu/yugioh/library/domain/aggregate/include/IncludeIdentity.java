package cn.mayu.yugioh.library.domain.aggregate.include;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IncludeIdentity extends ValueObject {

    private long id;

    public IncludeIdentity() {
        this.id = SnowFlake.nextId();
    }
}
