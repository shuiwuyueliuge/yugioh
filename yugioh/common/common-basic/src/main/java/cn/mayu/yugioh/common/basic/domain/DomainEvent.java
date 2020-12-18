package cn.mayu.yugioh.common.basic.domain;

import lombok.Getter;
import lombok.ToString;
import java.io.Serializable;
import java.util.EventObject;

@Getter
@ToString
public class DomainEvent<T> extends EventObject implements Serializable {

    /**
     * 事件id
     */
    private long eventId;

    /**
     * 事件发生时间
     */
    private long occurredOn;

    /**
     * 事件类型
     */
    private String type;

    /**
     * 事件数据
     */
    private T body;

    /**
     * 事件的路由键
     */
    private String routingKey;

    private Object dataCenterEnum;

    public DomainEvent(long eventId, long occurredOn, String type,
                       T body, Object source, String routingKey,
                       Object dataCenterEnum) {
        super(source);
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.type = type;
        this.body = body;
        this.routingKey = routingKey;
        this.dataCenterEnum = dataCenterEnum;
    }

    public DomainEvent() {
        super("");
    }
}
