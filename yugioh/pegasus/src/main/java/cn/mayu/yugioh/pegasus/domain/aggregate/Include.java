package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Include extends ValueObject {

    private String packageName;

    private String SaleDate;

    private String serial;

    private String rare;

    private String packShortName;
}
