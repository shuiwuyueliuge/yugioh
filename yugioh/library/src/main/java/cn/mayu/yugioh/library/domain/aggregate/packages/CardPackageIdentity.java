package cn.mayu.yugioh.library.domain.aggregate.packages;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CardPackageIdentity extends ValueObject {

    private String packageName;

    private String packShortName;
}
