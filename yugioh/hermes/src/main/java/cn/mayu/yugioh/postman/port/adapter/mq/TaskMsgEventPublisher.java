package cn.mayu.yugioh.postman.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(TaskMsgOutStream.class)
public class TaskMsgEventPublisher implements EventPublisherStrategy {

    @Autowired
    private TaskMsgOutStream taskMsgOutStream;

    public boolean publish(DomainEvent domainEvent) {
        return taskMsgOutStream.taskMsgOutput().send(MessageBuilder.withPayload(domainEvent).build(), 3000L);
    }

    @Override
    public String eventType() {
        return TaskMsgOutStream.TASK_MSG_OUTPUT;
    }
}