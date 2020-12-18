package cn.mayu.yugioh.postman.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CardMetaOutStream {

    String CARD_META_OUTPUT = "card-meta";

    @Output(CARD_META_OUTPUT)
    MessageChannel cardMetaOutput();
}