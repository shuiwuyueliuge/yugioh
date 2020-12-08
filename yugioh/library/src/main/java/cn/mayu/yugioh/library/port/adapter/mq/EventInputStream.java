package cn.mayu.yugioh.library.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EventInputStream {
	
	String EVENT_SAVE_INPUT = "event";
	
	@Input(EVENT_SAVE_INPUT)
	SubscribableChannel eventSaveInput();
}