package cn.mayu.yugioh.ceres.application;

import cn.mayu.yugioh.ceres.application.command.TaskCreateCommand;
import cn.mayu.yugioh.ceres.domain.aggregate.task.*;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * @description: 任务命令服务
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
@Service
@AllArgsConstructor
public class TaskCommandService {

    private final TaskRepository taskRepository;

    private final Environment environment;

    /**
     * 创建任务
     * 新建任务为就绪状态使用调度器去选择任务执行
     *
     * @param taskCreateCommand 任务需要的参数
     */
    public void createTask(TaskCreateCommand taskCreateCommand) {
        String port = environment.getProperty("server.port");
        String ip = environment.getProperty("spring.cloud.client.ip-address");
        // TODO 可能会出现大量无用的任务 -> 拦截策略
        TaskIdentity taskIdentity = new TaskIdentity(taskCreateCommand.getDataCenter(), UUID.randomUUID().toString(), ip + port);
        Task dataCenterTask = new Task(taskIdentity, new TaskStopwatch(System.currentTimeMillis()), taskCreateCommand.getOperateChannel());
        taskRepository.store(dataCenterTask);
        dataCenterTask.ready();
    }
}