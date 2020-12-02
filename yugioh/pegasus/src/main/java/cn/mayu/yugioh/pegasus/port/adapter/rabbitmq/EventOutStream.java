package cn.mayu.yugioh.pegasus.port.adapter.rabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EventOutStream {

    String EVENT_OUTPUT = "event";

    @Output(EVENT_OUTPUT)
    MessageChannel eventOutput();
}