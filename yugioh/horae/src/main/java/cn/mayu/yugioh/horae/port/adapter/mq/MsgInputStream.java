package cn.mayu.yugioh.horae.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MsgInputStream {
	
	String MSG_INPUT = "msg";
	
	@Input(MSG_INPUT)
	SubscribableChannel msgInput();
}