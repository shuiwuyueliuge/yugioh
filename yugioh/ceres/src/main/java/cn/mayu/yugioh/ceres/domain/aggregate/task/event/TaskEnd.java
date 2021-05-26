package cn.mayu.yugioh.ceres.domain.aggregate.task.event;

import cn.mayu.yugioh.ceres.domain.aggregate.task.Task;
import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 任务结束事件
 * @author: YgoPlayer
 * @time: 2021/5/12 5:44 下午
 */
@Data
@AllArgsConstructor
public class TaskEnd implements DomainEvent {

    private Task task;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}
