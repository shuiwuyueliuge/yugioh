package cn.mayu.yugioh.pegasus.application.subscribe;

import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.TaskRepository;
import cn.mayu.yugioh.pegasus.domain.aggregate.DataCenterTask;
import cn.mayu.yugioh.pegasus.domain.aggregate.IncludeCenterTaskCreated;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaData;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncludeTaskCreatedEventSubscribe implements DomainEventSubscribe<IncludeCenterTaskCreated> {

    @Autowired
    private DataCenterStrategy dataCenterStrategy;

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void subscribe(IncludeCenterTaskCreated includeCenterTaskCreated) {
        if (!"".equals(includeCenterTaskCreated.getOperateChannel())) {
            // 发布任务开始日志
            RemoteDomainEvent startDomainEvent = new RemoteDomainEvent(
                    System.currentTimeMillis(),
                    "task-msg",
                    String.format("Start from the %s platform transfer %s data task",
                            includeCenterTaskCreated.getDataCenterTaskIdentity().getDataCenter(),
                            includeCenterTaskCreated.getDataCenterTaskIdentity().getType()
                    ),
                    includeCenterTaskCreated.getOperateChannel()
            );

            eventFacade.receiveEvent(new EventReceiveCommand(startDomainEvent));
        }

        DataCenterFactory dataCenter = dataCenterStrategy.findDataCenter(
                includeCenterTaskCreated.getDataCenterTaskIdentity().getDataCenter()
        );

        // 获取收录信息
        List<MetaData<IncludeDTO>> metaDataList = dataCenter.getIncludeData().obtainIncludes(
                includeCenterTaskCreated.getCardPassword(),
                includeCenterTaskCreated.getResource()
        );

        if (metaDataList.size() == 0) {
            return;
        }

        if (!"".equals(includeCenterTaskCreated.getOperateChannel())) {
            // 发布执行日志
            RemoteDomainEvent runDomainEvent = new RemoteDomainEvent(
                    System.currentTimeMillis(),
                    "task-msg",
                    String.format("transfer success [%s]",
                            JsonConstructor.defaultInstance().writeValueAsString(metaDataList)
                    ),
                    includeCenterTaskCreated.getOperateChannel()
            );

            eventFacade.receiveEvent(new EventReceiveCommand(runDomainEvent));
        }

        metaDataList.forEach(metaData -> metaData.createMetaData());
        if (!"".equals(includeCenterTaskCreated.getOperateChannel())) {
            // 任务结束保存任务状态
            DataCenterTask dataCenterTask = new DataCenterTask(
                    includeCenterTaskCreated.getDataCenterTaskIdentity(),
                    includeCenterTaskCreated.getOperateChannel()
            );

            dataCenterTask.finish();
            taskRepository.store(dataCenterTask);
        }

        if (!"".equals(includeCenterTaskCreated.getOperateChannel())) {
            // 发布执行日志
            RemoteDomainEvent endDomainEvent = new RemoteDomainEvent(
                    System.currentTimeMillis(),
                    "task-msg",
                    String.format("end from the %s platform transfer %s data task",
                            includeCenterTaskCreated.getDataCenterTaskIdentity().getDataCenter(),
                            includeCenterTaskCreated.getDataCenterTaskIdentity().getType()
                    ),
                    includeCenterTaskCreated.getOperateChannel()
            );

            eventFacade.receiveEvent(new EventReceiveCommand(endDomainEvent));
        }
    }

    @Override
    public Class<IncludeCenterTaskCreated> domainEventClass() {
        return IncludeCenterTaskCreated.class;
    }
}
