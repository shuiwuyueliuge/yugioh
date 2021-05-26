package cn.mayu.yugioh.ceres.domain.aggregate.task.event;

import cn.mayu.yugioh.ceres.domain.aggregate.task.TaskIdentity;
import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 任务就绪事件
 * @author: YgoPlayer
 * @time: 2021/5/11 11:12 上午
 */
@Data
@AllArgsConstructor
public class TaskReady implements DomainEvent {

    private TaskIdentity taskIdentity;

    private Long startTime;

    @Override
    public long occurredOn() {
        return getStartTime();
    }
}