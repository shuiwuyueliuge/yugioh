package cn.mayu.yugioh.ceres.domain.aggregate.task;

import cn.mayu.yugioh.ceres.domain.aggregate.task.event.TaskCompleted;
import cn.mayu.yugioh.ceres.domain.aggregate.task.event.TaskReady;
import cn.mayu.yugioh.ceres.domain.aggregate.task.event.TaskRunning;
import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @description: 任务实体
 * @author: YgoPlayer
 * @time: 2021/5/11 11:12 上午
 */
@Getter
@AllArgsConstructor
@ToString
public class Task extends Entity {

    private final TaskIdentity taskIdentity;

    private final String operateChannel;

    private TaskStopwatch stopwatch;

    private TaskStatusEnum status;

    public Task(TaskIdentity taskIdentity, TaskStopwatch stopwatch, String operateChannel) {
        this.taskIdentity = taskIdentity;
        this.stopwatch = stopwatch;
        this.operateChannel = operateChannel;
    }

    /**
     * 任务就绪
     */
    public void ready() {
        status = TaskStatusEnum.READY;
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new TaskReady(
                this.taskIdentity,
                this.getStopwatch().getStartTime()
        ));
    }

    /**
     * 任务运行
     */
    public void running() {
        status = TaskStatusEnum.RUNNING;
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new TaskRunning(this));
    }

    /**
     * 任务完成
     */
    public void complete() {
        status = TaskStatusEnum.COMPLETE;
        stopwatch = new TaskStopwatch(stopwatch.getStartTime(), System.currentTimeMillis());
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new TaskCompleted(this));
    }
}