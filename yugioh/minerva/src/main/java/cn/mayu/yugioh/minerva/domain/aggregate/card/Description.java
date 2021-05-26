package cn.mayu.yugioh.minerva.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Description extends ValueObject {

    // 效果
    private final String desc;

    // nw效果
    private final String descNw;

    // 日文效果
    private final String descJa;

    // 英文效果
    private final String descEn;
}
