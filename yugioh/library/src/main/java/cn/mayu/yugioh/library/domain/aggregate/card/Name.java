package cn.mayu.yugioh.library.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Name extends ValueObject {

    // 中文名
    private String name;

    // 日文名
    private String nameJa;

    // 英文名
    private String nameEn;

    // nw名称
    private String nameNw;
}
