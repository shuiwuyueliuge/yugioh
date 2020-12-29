package cn.mayu.yugioh.postman.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TaskMsgOutStream {

    String TASK_MSG_OUTPUT = "task-msg";

    @Output(TASK_MSG_OUTPUT)
    MessageChannel taskMsgOutput();
}