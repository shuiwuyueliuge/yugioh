package cn.mayu.yugioh.horae.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(MsgInputStream.class)
public class MsgEventReceiver {
	@StreamListener(MsgInputStream.MSG_INPUT)
	public void receiveSave(Message<String> message) {
		RemoteDomainEvent domainEvent = JsonParser.defaultInstance().readObjectValue(message.getPayload(), RemoteDomainEvent.class);
		System.out.println(domainEvent.getPayload());
	}
}