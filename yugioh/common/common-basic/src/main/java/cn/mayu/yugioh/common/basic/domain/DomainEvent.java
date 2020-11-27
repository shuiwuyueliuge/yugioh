package cn.mayu.yugioh.common.basic.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.EventObject;

@Getter
public class DomainEvent<T> extends EventObject {

    /**
     * 事件id
     */
    private String eventId;

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

    public DomainEvent(String eventId, String type, T body, Object source) {
        super(source);
        this.eventId = eventId;
        this.occurredOn = LocalDateTime.now();
        this.type = type;
        this.body = body;
    }
}
