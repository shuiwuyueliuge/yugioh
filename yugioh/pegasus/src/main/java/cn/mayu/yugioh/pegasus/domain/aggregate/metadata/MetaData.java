package cn.mayu.yugioh.pegasus.domain.aggregate.metadata;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 卡片/禁卡表元数据
 */
@Getter
@AllArgsConstructor
public class MetaData extends Entity {

    // 元数据唯一标识
    private MetaDataIdentity metaDataIdentity;
    
    // 元数据详情
    private String data;

    /**
     * 发布领域命令，消息推送给订阅者组装成对应的数据发送到mq
     */
    public void commitTo() {
    }
}
