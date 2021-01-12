package cn.mayu.yugioh.pegasus.application.subscribe;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.IncludeCenterTaskCreated;
import cn.mayu.yugioh.pegasus.domain.aggregate.TaskRepository;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.EventEnum;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class IncludeTaskCreatedEventSubscribe extends TaskMessage<IncludeDTO, IncludeCenterTaskCreated> {

    public IncludeTaskCreatedEventSubscribe(DataCenterStrategy dataCenterStrategy,
                                            EventFacade eventFacade,
                                            TaskRepository taskRepository) {
        super(dataCenterStrategy, eventFacade, taskRepository);
    }

    @Override
    protected Iterator<List<IncludeDTO>> getData(DataCenterFactory dataCenter, IncludeCenterTaskCreated mainEvent) {
        return new Iterator<List<IncludeDTO>>(){

            private boolean next = true;

            @Override
            public boolean hasNext() {
                boolean hasNext = next;
                next = false;
                return hasNext;
            }

            @Override
            public List<IncludeDTO> next() {
                List<IncludeDTO> metaDataList = dataCenter.getIncludeData()
                        .obtainIncludes(mainEvent.getCardPassword(), mainEvent.getResource());
                if (metaDataList.size() == 0) {
                    return Lists.newArrayList();
                }

                return metaDataList;
            }
        };
    }

    @Override
    protected RemoteDomainEvent publishResult(IncludeDTO data, long occurredOn) {
        return new RemoteDomainEvent(
                occurredOn,
                EventEnum.INCLUDE.getType(),
                JsonConstructor.defaultInstance().writeValueAsString(data),
                data.getPassword()
        );
    }

    @Override
    protected void sendResultMsg(Map<String, String> map, IncludeDTO data) {
        map.put("include", data.toString());
    }

    @Override
    public Class<IncludeCenterTaskCreated> domainEventClass() {
        return IncludeCenterTaskCreated.class;
    }
}
