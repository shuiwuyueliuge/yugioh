package cn.mayu.yugioh.hermes.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 事件溯源创建事件
 * @author: YgoPlayer
 * @time: 2021/5/17 11:31 上午
 */
@Data
@AllArgsConstructor
public class EventSourcingCreated implements DomainEvent {

    private final long eventId;

    private final long occurredOn;

    private final String payload;

    private final String type;

    private final String routingKey;

    @Override
    public long occurredOn() {
        return occurredOn;
    }
}
