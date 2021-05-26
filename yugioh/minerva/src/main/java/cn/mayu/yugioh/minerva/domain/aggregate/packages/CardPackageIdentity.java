package cn.mayu.yugioh.minerva.domain.aggregate.packages;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CardPackageIdentity extends ValueObject {

    private final String packageName;

    private final String packShortName;
}
