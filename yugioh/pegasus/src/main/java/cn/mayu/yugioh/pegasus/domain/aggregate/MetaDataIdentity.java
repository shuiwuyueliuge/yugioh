package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MetaDataIdentity extends ValueObject {

    private DataCenterEnum centerEnum;

    private String type;

    public MetaDataIdentity(DataCenterEnum centerEnum, String type) {
        this.centerEnum = centerEnum;
        this.type = type;
    }
}
