package cn.mayu.yugioh.pegasus.application.subscribe;

import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaDataCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetaEventSubscribe implements DomainEventSubscribe<MetaDataCreated> {

    @Autowired
    private EventFacade eventFacade;

    @Override
    public void subscribe(MetaDataCreated metaDataCreated) {
        RemoteDomainEvent remoteDomainEvent = new RemoteDomainEvent(
                metaDataCreated.occurredOn(),
                metaDataCreated.getMetaDataIdentity().getType(),
                JsonConstructor.defaultInstance().writeValueAsString(metaDataCreated.getData()),
                metaDataCreated.getMetaDataIdentity().getKey()
        );

        eventFacade.receiveEvent(new EventReceiveCommand(remoteDomainEvent));
    }

    @Override
    public Class<MetaDataCreated> domainEventClass() {
        return MetaDataCreated.class;
    }
}
