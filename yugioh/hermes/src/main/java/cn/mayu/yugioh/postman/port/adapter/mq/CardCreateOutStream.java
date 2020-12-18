package cn.mayu.yugioh.postman.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CardCreateOutStream {

    String CARD_CREATE_OUTPUT = "card-create";

    @Output(CARD_CREATE_OUTPUT)
    MessageChannel cardCreateOutput();
}