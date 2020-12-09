package cn.mayu.yugioh.library.application.card.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateCommand {

    // 卡片密码
    private String password;

    // 中文名
    private String name;

    // 日文名
    private String nameJa;

    // 英文名
    private String nameEn;

    // nw名称
    private String nameNw;

    // 图片地址
    private String imgUrl;

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

    // 效果
    private String desc;

    // 限制
    private String ban;

    // nw效果
    private String descNw;

    // 日文效果
    private String descJa;

    // 英文效果
    private String descEn;

    // 卡片类型
    private List<String> typeSt;

    // 连接方向
    private List<String> linkArrow;

    // wiki
    private String adjust;

    // 卡类型
    private String typeVal;
}
