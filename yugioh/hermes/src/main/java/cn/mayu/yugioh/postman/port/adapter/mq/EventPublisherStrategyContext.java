package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class EventPublisherStrategyContext {

    private Set<EventPublisherStrategy> eventPublisherStrategies;

    public EventPublisherStrategyContext(Set<EventPublisherStrategy> eventPublisherStrategies) {
        this.eventPublisherStrategies = eventPublisherStrategies;
    }

    public boolean publish(DomainEvent domainEvent){
        for(EventPublisherStrategy publisher : eventPublisherStrategies) {
            if (domainEvent.getType().equals(publisher.eventType())) {
                return publisher.publish(domainEvent);
            }
        }

        return false;
    }
}
