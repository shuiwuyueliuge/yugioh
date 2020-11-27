package cn.mayu.yugioh.common.basic.event.sourcing;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnBean({EventStore.class})
public class EventSourcingAutoConfiguration {

    @Bean
    public EventSourcingInterceptor initEventSourcingInterceptor(EventStore eventStore) {
        return new EventSourcingInterceptor(eventStore);
    }
}
