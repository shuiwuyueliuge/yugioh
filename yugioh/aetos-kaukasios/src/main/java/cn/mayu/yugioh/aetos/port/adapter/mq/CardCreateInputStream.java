package cn.mayu.yugioh.aetos.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CardCreateInputStream {
	
	String CARD_CREATE_INPUT = "card-create";
	
	@Input(CARD_CREATE_INPUT)
	SubscribableChannel cardCreateInput();
}