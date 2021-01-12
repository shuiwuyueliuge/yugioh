package cn.mayu.yugioh.pegasus.application.subscribe;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.CardCenterTaskCreated;
import cn.mayu.yugioh.pegasus.domain.aggregate.TaskRepository;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.EventEnum;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class CardTaskCreatedEventSubscribe extends TaskMessage<CardDTO, CardCenterTaskCreated> {

    public CardTaskCreatedEventSubscribe(DataCenterStrategy dataCenterStrategy,
                                         EventFacade eventFacade,
                                         TaskRepository taskRepository) {
        super(dataCenterStrategy, eventFacade, taskRepository);
    }

    @Override
    protected Iterator<List<CardDTO>> getData(DataCenterFactory dataCenter, CardCenterTaskCreated mainEvent) {
        return dataCenter.getCardData().obtainCards(mainEvent.getDataCenterTaskIdentity().toString());
    }

    @Override
    public Class<CardCenterTaskCreated> domainEventClass() {
        return CardCenterTaskCreated.class;
    }

    @Override
    protected RemoteDomainEvent publishResult(CardDTO data, long occurredOn) {
        return new RemoteDomainEvent(
                occurredOn,
                EventEnum.CARD.getType(),
                JsonConstructor.defaultInstance().writeValueAsString(data),
                data.getPassword()
        );
    }

    @Override
    protected void sendResultMsg(Map<String, String> map, CardDTO data) {
        map.put("name", data.getNameNw());
        map.put("type", data.getTypeVal());
    }
}
