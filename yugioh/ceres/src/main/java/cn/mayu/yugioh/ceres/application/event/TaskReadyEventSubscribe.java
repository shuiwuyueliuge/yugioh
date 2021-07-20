package cn.mayu.yugioh.ceres.application.event;

import cn.mayu.yugioh.ceres.domain.aggregate.task.Task;
import cn.mayu.yugioh.ceres.domain.aggregate.task.TaskRepository;
import cn.mayu.yugioh.ceres.domain.aggregate.task.TaskStatusEnum;
import cn.mayu.yugioh.ceres.domain.aggregate.task.event.TaskReady;
import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

/**
 * @description: 任务就绪事件订阅
 * @author: YgoPlayer
 * @time: 2021/5/11 3:23 下午
 */
@Slf4j
@Component
public class TaskReadyEventSubscribe implements DomainEventSubscribe<TaskReady>, Runnable {

    private final TaskRepository taskRepository;

    private Thread execute;

    private volatile Task currTask;

    private String ipPort;

    public TaskReadyEventSubscribe(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void subscribe(TaskReady taskCreated) {
        if (log.isDebugEnabled()) {
            log.debug("task created: {}", taskCreated);
        }

        synchronized (TaskReadyEventSubscribe.class) {
            if (Objects.isNull(execute)) {
                this.execute = new Thread(this);
            } else {
                if (Objects.equals(execute.getState(), Thread.State.RUNNABLE)) {
                    return;
                }
            }
        }

        String dataCenter = taskCreated.getTaskIdentity().getDataCenter();
        String uuid = taskCreated.getTaskIdentity().getUuid();
        ipPort = taskCreated.getTaskIdentity().getIpPort();
        // 执行当前任务
        currTask = taskRepository.findByDataCenterAndUuid(dataCenter, uuid);
        if (Objects.equals(execute.getState(), Thread.State.NEW)) {
            execute.start();
            return;
        }

        LockSupport.unpark(execute);
    }

    @Override
    public void run() {
        while(true) {
            if (Objects.isNull(currTask)) {
                LockSupport.park();
            }

            currTask.running();
            List<Task> nextList = taskRepository.findByIpPortAndStatus(ipPort, TaskStatusEnum.READY, 0, 1);
            while(!nextList.isEmpty()) {
                Task next = nextList.get(0);
                next.running();
                nextList = taskRepository.findByIpPortAndStatus(ipPort, TaskStatusEnum.READY, 0, 1);
            }

            currTask = null; // 处理虚假唤醒
        }
    }

    @Override
    public Class<TaskReady> domainEventClass() {
        return TaskReady.class;
    }
}
