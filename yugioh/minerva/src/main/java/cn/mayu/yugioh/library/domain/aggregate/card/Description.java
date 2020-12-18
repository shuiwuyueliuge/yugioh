package cn.mayu.yugioh.library.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Description extends ValueObject {

    // 效果
    private String desc;

    // nw效果
    private String descNw;

    // 日文效果
    private String descJa;

    // 英文效果
    private String descEn;
}
