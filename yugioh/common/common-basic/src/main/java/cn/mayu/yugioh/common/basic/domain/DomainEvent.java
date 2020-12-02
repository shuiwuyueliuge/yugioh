package cn.mayu.yugioh.common.basic.domain;

import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.EventObject;

@Getter
@ToString
public class DomainEvent<T> extends EventObject {

    /**
     * 事件id
     */
    private long eventId;

    /**
     * 事件发生时间
     */
    private LocalDateTime occurredOn;

    /**
     * 事件类型
     */
    private String type;

    /**
     * 事件数据
     */
    private T body;

    public DomainEvent(String type, T body) {
        super(body);
        this.eventId = SnowFlake.nextId();
        this.occurredOn = LocalDateTime.now();
        this.type = type;
        this.body = body;
    }

    public DomainEvent(long eventId, LocalDateTime occurredOn, String type, T body) {
        super("");
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.type = type;
        this.body = body;
    }
}
