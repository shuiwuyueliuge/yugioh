package cn.mayu.yugioh.pegasus.application.subscribe;

import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.TaskRepository;
import cn.mayu.yugioh.pegasus.domain.aggregate.CardCenterTaskCreated;
import cn.mayu.yugioh.pegasus.domain.aggregate.DataCenterTask;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.EventEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class CardTaskCreatedEventSubscribe implements DomainEventSubscribe<CardCenterTaskCreated> {

    @Autowired
    private DataCenterStrategy dataCenterStrategy;

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void subscribe(CardCenterTaskCreated dataCenterTaskCreated) {
        // 发布任务开始日志
        RemoteDomainEvent startDomainEvent = new RemoteDomainEvent(
                System.currentTimeMillis(),
                "task-msg",
                String.format("Start from the %s platform transfer %s data task",
                        dataCenterTaskCreated.getDataCenterTaskIdentity().getDataCenter(),
                        dataCenterTaskCreated.getDataCenterTaskIdentity().getType()
                ),
                dataCenterTaskCreated.getOperateChannel()
        );

        eventFacade.receiveEvent(new EventReceiveCommand(startDomainEvent));

        // 获取数据中心
        DataCenterFactory dataCenter = dataCenterStrategy.findDataCenter(dataCenterTaskCreated.getDataCenterTaskIdentity().getDataCenter());
        // 获取卡片信息
        Iterator<List<CardDTO>> cardIterator = dataCenter.getCardData().obtainCards(dataCenterTaskCreated.getDataCenterTaskIdentity().toString());
        while (cardIterator.hasNext()) {
            List<CardDTO> metaData = cardIterator.next();
            // 发布执行日志
            List<Map<String, String>> mapList = Lists.newArrayList();
            metaData.forEach(data -> {
                Map<String, String> map = Maps.newHashMap();
                map.put("name", data.getNameNw());
                map.put("type", data.getTypeVal());
                mapList.add(map);
            });

            RemoteDomainEvent runDomainEvent = new RemoteDomainEvent(
                    System.currentTimeMillis(),
                    "task-msg",
                    String.format("transfer success [%s]",
                            JsonConstructor.defaultInstance().writeValueAsString(mapList)
                    ),
                    dataCenterTaskCreated.getOperateChannel()
            );

            eventFacade.receiveEvent(new EventReceiveCommand(runDomainEvent));

            for (CardDTO data : metaData) {
                RemoteDomainEvent remoteDomainEvent = new RemoteDomainEvent(
                        dataCenterTaskCreated.getDataCenterTaskIdentity().getStartTime(),
                        EventEnum.CARD.getType(),
                        JsonConstructor.defaultInstance().writeValueAsString(data),
                        data.getPassword()
                );

                eventFacade.receiveEvent(new EventReceiveCommand(remoteDomainEvent));
            }
        }

        // 任务结束保存任务状态
        DataCenterTask dataCenterTask = new DataCenterTask(
                dataCenterTaskCreated.getDataCenterTaskIdentity(),
                dataCenterTaskCreated.getOperateChannel(),
                dataCenterTaskCreated.getParentTask()
        );

        dataCenterTask.finish();
        taskRepository.store(dataCenterTask);

        // 发布执行日志
        RemoteDomainEvent endDomainEvent = new RemoteDomainEvent(
                System.currentTimeMillis(),
                "task-msg",
                String.format("end from the %s platform transfer %s data task",
                        dataCenterTaskCreated.getDataCenterTaskIdentity().getDataCenter(),
                        dataCenterTaskCreated.getDataCenterTaskIdentity().getType()
                ),
                dataCenterTaskCreated.getOperateChannel()
        );

        eventFacade.receiveEvent(new EventReceiveCommand(endDomainEvent));
    }

    @Override
    public Class<CardCenterTaskCreated> domainEventClass() {
        return CardCenterTaskCreated.class;
    }
}
