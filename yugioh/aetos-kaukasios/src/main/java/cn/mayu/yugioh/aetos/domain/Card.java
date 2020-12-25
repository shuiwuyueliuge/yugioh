package cn.mayu.yugioh.aetos.domain;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Card extends Entity {

    private CardIdentity cardIdentity;

    private Monster monster;

    private String name;

    private String desc;

    private String typeVal;

    private List<String> typeSt;
}
