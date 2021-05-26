package cn.mayu.yugioh.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

public class RedisAutoConfiguration {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("lock_wakeup"));
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(LockObservable lockObservable){
        return new MessageListenerAdapter(lockObservable,"wakeup");
    }

    @Bean
    public LockObservable lockObservable(){
        return new LockObservable();
    }
}
