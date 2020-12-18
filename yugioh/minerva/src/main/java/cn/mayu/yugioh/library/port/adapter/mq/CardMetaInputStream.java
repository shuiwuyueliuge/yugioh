package cn.mayu.yugioh.library.port.adapter.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CardMetaInputStream {
	
	String CARD_META_INPUT = "card-meta";
	
	@Input(CARD_META_INPUT)
	SubscribableChannel cardMetaInput();
}