package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MetaDataIdentity extends ValueObject {

    private String center;

    private String type;

    private String key;

    public MetaDataIdentity(String key, String center, String type) {
        this.center = center;
        this.type = type;
        this.key = key;
    }
}
