package cn.mayu.yugioh.pegasus.domain.aggregate.metadata;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import java.util.UUID;

/**
 * 元数据唯一标识
 */
public class MetaDataIdentity extends ValueObject {

    private String identity;

    /**
     * 构建元数据的唯一标识
     * @param dataCenter    数据中心
     * @param dataType  数据类型
     */
    public MetaDataIdentity(String dataCenter, String dataType) {
        this.identity = String.format("%s_%s_%s", dataCenter, dataType, UUID.randomUUID().timestamp());
    }
}
