package cn.mayu.yugioh.library.application.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private String name;

    private String desc;

    private String typeVal;

    private String link;

    private String def;

    private String pend;

    private String race;

    private String password;

    private String attribute;

    private String level;

    private String atk;

    private List<String> typeSt;

    private List<String> linkArrow;
}
