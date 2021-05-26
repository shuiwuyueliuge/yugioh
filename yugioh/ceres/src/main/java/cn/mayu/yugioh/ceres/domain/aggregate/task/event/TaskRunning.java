package cn.mayu.yugioh.ceres.domain.aggregate.task.event;

import cn.mayu.yugioh.ceres.domain.aggregate.task.*;
import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 任务运行事件
 * @author: YgoPlayer
 * @time: 2021/5/11 11:12 上午
 */
@Data
@AllArgsConstructor
public class TaskRunning implements DomainEvent {

    private Task task;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}