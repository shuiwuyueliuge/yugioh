package cn.mayu.yugioh.minerva.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Name extends ValueObject {

    // 中文名
    private final String name;

    // 日文名
    private final String nameJa;

    // 英文名
    private final String nameEn;

    // nw名称
    private final String nameNw;
}
