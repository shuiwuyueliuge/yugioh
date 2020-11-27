package cn.mayu.yugioh.common.basic.event.sourcing;

import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 事件源
 */
@Getter
@ToString
public class Event {

    /**
     * 事件id
     */
    private Long eventId;

    /**
     * 事件发生时间
     */
    private LocalDateTime occurTime;

    /**
     * 事件类型
     */
    private String type;

    /**
     * 事件数据
     */
    private String body;

    public Event(String type, Object body) {
        this.eventId = SnowFlake.nextId();
        this.occurTime = LocalDateTime.now();
        this.type = type;
        this.body = JsonConstructor.defaultInstance().writeValueAsString(body);
    }
}
