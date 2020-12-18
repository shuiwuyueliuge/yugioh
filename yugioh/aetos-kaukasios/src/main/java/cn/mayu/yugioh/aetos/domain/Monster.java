package cn.mayu.yugioh.aetos.domain;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Monster extends ValueObject {

    private String link;

    private String def;

    private String pend;

    private String race;

    private String attribute;

    private String level;

    private String atk;

    private List<String> linkArrow;
}
