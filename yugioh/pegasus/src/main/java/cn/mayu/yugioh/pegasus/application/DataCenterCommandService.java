package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.command.IncludeInfoCreateCommand;
import cn.mayu.yugioh.pegasus.domain.aggregate.TaskRepository;
import cn.mayu.yugioh.pegasus.domain.aggregate.DataCenterTask;
import cn.mayu.yugioh.pegasus.domain.aggregate.DataCenterTaskIdentity;
import cn.mayu.yugioh.pegasus.exception.DataCenterTaskRunningException;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.EventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据中心命令服务
 */
@Service
public class DataCenterCommandService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * 创建card目录
     */
    public void createCardList(CardInfoCreateCommand cardListCreateCommand) {
        isRunning(cardListCreateCommand.getDataCenter(), "running", EventEnum.CARD.name());
        DataCenterTask dataCenterTask = new DataCenterTask(
                new DataCenterTaskIdentity(cardListCreateCommand.getDataCenter().name(), EventEnum.CARD.name()),
                cardListCreateCommand.getChannelId()
        );

        taskRepository.store(dataCenterTask);
        dataCenterTask.runCardTask();
    }

    /**
     * 创建收录目录
     */
    public void createIncludeInfo(IncludeInfoCreateCommand includeInfoCreateCommand) {
        isRunning(includeInfoCreateCommand.getDataCenter(), "running", EventEnum.INCLUDE.name());
        DataCenterTask dataCenterTask = new DataCenterTask(
                new DataCenterTaskIdentity(includeInfoCreateCommand.getDataCenter().name(), EventEnum.INCLUDE.name()),
                includeInfoCreateCommand.getChannelId()
        );

        if (!"".equals(includeInfoCreateCommand.getChannelId())) {
            taskRepository.store(dataCenterTask);
        }

        dataCenterTask.runIncludeTask(includeInfoCreateCommand.getCardPassword(), includeInfoCreateCommand.getResource());
    }

    private void isRunning(DataCenterEnum dataCenter, String running, String type) {
        DataCenterTask dataCenterTask = taskRepository.findByDataCenterAndStatus(dataCenter, running, type);
        if (dataCenterTask != null) {
            throw new DataCenterTaskRunningException(String.format("%s-%s任务运行中", dataCenter, type));
        }
    }
}