package cn.mayu.yugioh.pegasus.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import org.springframework.stereotype.Component;

@Component
public class CardInfoEventConsumer implements DomainEventConsumer {

    @Override
    public void subscribe(DomainEvent domainEvent) {
        System.out.println(domainEvent);
    }

    @Override
    public String getEventType() {
        return "createCardList";
    }
}
