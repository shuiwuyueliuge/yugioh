package cn.mayu.yugioh.pegasus.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardInfoEventConsumer implements DomainEventConsumer {

    @Autowired
    private EventStore eventStore;

    @Override
    public void subscribe(DomainEvent domainEvent) {
        eventStore.store(domainEvent);
    }

    @Override
    public String getEventType() {
        return "createCardList";
    }
}
