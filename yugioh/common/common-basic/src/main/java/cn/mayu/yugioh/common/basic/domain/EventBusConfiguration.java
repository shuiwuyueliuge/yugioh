package cn.mayu.yugioh.common.basic.domain;

import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import java.util.List;

@ConditionalOnBean(EventStore.class)
public class EventBusConfiguration {

    @Bean
    public DomainEventPublisher domainEventPublisher(EventStore eventStore, List<DomainEventConsumer> eventConsumers) {
        return new DomainEventPublisher(eventStore, eventConsumers);
    }
}
