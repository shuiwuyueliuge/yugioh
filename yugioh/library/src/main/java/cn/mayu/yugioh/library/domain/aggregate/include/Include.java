package cn.mayu.yugioh.library.domain.aggregate.include;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Include extends Entity {

    private String packageName;

    private String saleDate;

    private String serial;

    private String rare;

    private String packShortName;
}
