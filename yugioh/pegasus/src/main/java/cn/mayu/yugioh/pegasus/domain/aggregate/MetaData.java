package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MetaData extends Entity {

    private MetaDataIdentity metaDataIdentity;

    private String data;

    /**
     * 发布领域事件
     */
    public void commitTo() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new DomainEvent(
                SnowFlake.nextId(),
                System.currentTimeMillis(),
                metaDataIdentity.getType(),
                data,
                metaDataIdentity.getCenterEnum(),
                metaDataIdentity.getKey()
        ));
    }
}
