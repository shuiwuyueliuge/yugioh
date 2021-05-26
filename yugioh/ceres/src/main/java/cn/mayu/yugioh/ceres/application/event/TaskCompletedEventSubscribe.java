package cn.mayu.yugioh.ceres.application.event;

import cn.mayu.yugioh.ceres.domain.aggregate.task.TaskRepository;
import cn.mayu.yugioh.ceres.domain.aggregate.task.event.TaskCompleted;
import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: 任务完成事件订阅
 * @author: YgoPlayer
 * @time: 2021/5/11 3:23 下午
 */
@Slf4j
@Component
public class TaskCompletedEventSubscribe implements DomainEventSubscribe<TaskCompleted> {

    private final TaskRepository taskRepository;

    public TaskCompletedEventSubscribe(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public void subscribe(TaskCompleted taskCompleted) {
        taskRepository.store(taskCompleted.getTask());
    }

    @Override
    public Class<TaskCompleted> domainEventClass() {
        return TaskCompleted.class;
    }
}
