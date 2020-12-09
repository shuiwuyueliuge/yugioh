package cn.mayu.yugioh.common.basic.domain;

import lombok.Getter;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventObject;

@Getter
@ToString
public class DomainEvent<T> extends EventObject implements Serializable {

    /**
     * 事件id
     */
    private String eventId;

    /**
     * 事件发生时间
     */
    private String occurredOn;

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
        this.occurredOn = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.type = type;
        this.body = body;
    }

    public DomainEvent(String eventId, String occurredOn, String type, T body, Object source) {
        super(source);
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.type = type;
        this.body = body;
    }

    public DomainEvent() {
        super("");
    }
}
