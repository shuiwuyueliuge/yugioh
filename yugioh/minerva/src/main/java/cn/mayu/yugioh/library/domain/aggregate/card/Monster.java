package cn.mayu.yugioh.library.domain.aggregate.card;

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
    private String level;

    // 属性
    private String attribute;

    // 种族
    private String race;

    // 攻击力
    private String atk;

    // 防御力
    private String def;

    // 左刻度
    private String pend;

    // 链接数
    private String link;

    // 连接方向
    private List<String> linkArrow;
}