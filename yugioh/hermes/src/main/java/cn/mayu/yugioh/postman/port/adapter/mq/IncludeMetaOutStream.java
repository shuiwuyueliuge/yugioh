package cn.mayu.yugioh.postman.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface IncludeMetaOutStream {

    String INCLUDE_META_OUTPUT = "include-meta";

    @Output(INCLUDE_META_OUTPUT)
    MessageChannel includeMetaOutput();
}