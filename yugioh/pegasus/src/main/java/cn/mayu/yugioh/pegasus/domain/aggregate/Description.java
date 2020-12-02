package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Description extends ValueObject {

    // 效果
    private String desc;

    // 日文效果
    private String descJa;

    // 英文效果
    private String descEn;

    // nw效果
    private String descNw;
}
