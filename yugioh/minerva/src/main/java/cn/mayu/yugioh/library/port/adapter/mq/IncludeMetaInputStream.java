package cn.mayu.yugioh.library.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IncludeMetaInputStream {
	
	String INCLUDE_META_INPUT = "include-meta";
	
	@Input(INCLUDE_META_INPUT)
	SubscribableChannel includeMetaInput();
}