package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.postman.domain.aggregate.EventSourcing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(EventOutStream.class)
public class EventPublisher {

    @Autowired
    private EventOutStream eventOutStream;

    public boolean publish(EventSourcing eventSourcing) {
        return eventOutStream.eventOutput().send(MessageBuilder.withPayload(eventSourcing).build(), 3000L);
    }
}