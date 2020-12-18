package cn.mayu.yugioh.postman.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MsgOutStream {

    String MSG_OUTPUT = "msg";

    @Output(MSG_OUTPUT)
    MessageChannel msgOutput();
}