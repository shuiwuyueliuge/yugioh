package cn.mayu.yugioh.minerva.port.adapter.persistence.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_card", uniqueConstraints =
    @UniqueConstraint(columnNames = {"password", "name_nw"})
)
public class CardDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 卡片密码
    @Column(name = "password")
    private String password;

    // 中文名
    @Column(name = "name")
    private String name;

    // 日文名
    @Column(name = "name_ja")
    private String nameJa;

    // 英文名
    @Column(name = "name_en")
    private String nameEn;

    // nw名称
    @Column(name = "name_nw")
    private String nameNw;

    // 图片地址
    @Column(name = "img_url")
    private String imgUrl;

    // 等级
    @Column(name = "level")
    private String level;

    // 属性
    @Column(name = "attribute")
    private String attribute;

    // 种族
    @Column(name = "race")
    private String race;

    // 攻击力
    @Column(name = "atk")
    private String atk;

    // 防御力
    @Column(name = "def")
    private String def;

    // 刻度
    @Column(name = "pend")
    private String pend;

    // 链接数
    @Column(name = "link")
    private String link;

    // 效果
    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    // nw效果
    @Column(name = "desc_nw", columnDefinition="TEXT")
    private String descNw;

    // 日文效果
    @Column(name = "desc_ja", columnDefinition="TEXT")
    private String descJa;

    // 英文效果
    @Column(name = "desc_en", columnDefinition="TEXT")
    private String descEn;

    // 卡片类型
    @Column(name = "type_st")
    private String typeSt;

    // 连接方向
    @Column(name = "link_arrow")
    private String linkArrow;

    // wiki
    @Column(name = "adjust", columnDefinition="TEXT")
    private String adjust;

    // 卡类型
    @Column(name = "type_val")
    private String typeVal;

    // 收录信息
    @Column(name = "include", columnDefinition = "TEXT")
    private String include;
}
