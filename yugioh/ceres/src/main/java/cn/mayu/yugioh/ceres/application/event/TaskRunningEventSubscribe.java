package cn.mayu.yugioh.ceres.application.event;

import cn.mayu.yugioh.ceres.application.dto.SyncCardResult;
import cn.mayu.yugioh.ceres.domain.aggregate.task.Task;
import cn.mayu.yugioh.ceres.domain.aggregate.task.TaskRepository;
import cn.mayu.yugioh.ceres.application.DataCenterService;
import cn.mayu.yugioh.ceres.domain.aggregate.task.event.TaskRunning;
import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonCreator;
import cn.mayu.yugioh.common.facade.hermes.EventFacade;
import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import cn.mayu.yugioh.common.facade.minerva.model.CardCreateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.util.Date;

/**
 * @description: 任务运行事件订阅
 * @author: YgoPlayer
 * @time: 2021/5/11 3:23 下午
 */
@Slf4j
@Component
public class TaskRunningEventSubscribe implements DomainEventSubscribe<TaskRunning> {

    private final EventFacade eventFacade;

    private final TaskRepository taskRepository;

    private final DataCenterService taskService;

    public TaskRunningEventSubscribe(
            TaskRepository taskRepository,
            DataCenterService taskService,
            EventFacade eventFacade
    ) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
        this.eventFacade = eventFacade;
    }

    @Override
    public void subscribe(TaskRunning taskRunning) {
        Task task = taskRunning.getTask();
        taskRepository.store(task);
        Flux<SyncCardResult> resultFlux = taskService.syncCardList(task);
        resultFlux.subscribe(
                result -> {
                    sendCardCreateCommand(result.getCardCreateCommand());
                    sendTaskProgress(task, String.format(
                            "时间: %s 卡片总数：%s 同步数量：%s 进度：%s",
                            new Date().toLocaleString(),
                            result.getCardCount(),
                            result.getCatchCount(),
                            result.getCompletionRate() + "%"
                    ));
                },
                throwable -> {
                    String errorMsg = String.format("task: [%s] running error:", task.getTaskIdentity().toString());
                    log.error(errorMsg, throwable);
                    task.complete();
                    sendTaskProgress(task, throwable.getMessage());
                },
                () -> {
                    task.complete();
                    sendTaskProgress(task, "complete");
                }
        );
    }

    private void sendCardCreateCommand(CardCreateCommand cardCreateCommand) {
        RemoteDomainEvent runDomainEvent = new RemoteDomainEvent(
                System.currentTimeMillis(),
                "data-storm-in-0",
                JsonCreator.defaultInstance().writeValueAsString(cardCreateCommand)
        );

        eventFacade.receiveEvent(new EventReceiveCommand(runDomainEvent));
    }

    private void sendTaskProgress(Task task, String taskProgressInfo) {
        RemoteDomainEvent runDomainEvent = new RemoteDomainEvent(
                System.currentTimeMillis(),
                "task-progress-in-0",
                "{\"taskId\":\"" + task.getTaskIdentity().getUuid() + "\",\"taskInfo\":\"" + taskProgressInfo + "\"}",
                task.getOperateChannel()
        );

        eventFacade.receiveEvent(new EventReceiveCommand(runDomainEvent));
    }

    @Override
    public Class<TaskRunning> domainEventClass() {
        return TaskRunning.class;
    }
}