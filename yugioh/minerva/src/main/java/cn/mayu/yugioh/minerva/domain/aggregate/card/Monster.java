package cn.mayu.yugioh.minerva.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class Monster extends ValueObject {

    // 等级
    private final String level;

    // 属性
    private final String attribute;

    // 种族
    private final String race;

    // 攻击力
    private final String atk;

    // 防御力
    private final String def;

    // 左刻度
    private final String pend;

    // 链接数
    private final String link;

    // 连接方向
    private final List<String> linkArrow;
}
