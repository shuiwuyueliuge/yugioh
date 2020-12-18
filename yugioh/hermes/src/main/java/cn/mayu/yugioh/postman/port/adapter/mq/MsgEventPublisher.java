package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MsgOutStream.class)
public class MsgEventPublisher implements EventPublisherStrategy {

    @Autowired
    private MsgOutStream msgOutStream;

    public boolean publish(DomainEvent domainEvent) {
        return msgOutStream.msgOutput().send(MessageBuilder.withPayload(domainEvent).build(), 3000L);
    }

    @Override
    public String eventType() {
        return MsgOutStream.MSG_OUTPUT;
    }
}