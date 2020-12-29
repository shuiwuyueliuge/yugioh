package cn.mayu.yugioh.horae.port.adapter.mq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(MsgInputStream.class)
public class MsgEventReceiver {
	@StreamListener(MsgInputStream.MSG_INPUT)
	public void receiveSave(Message<String> message) {
		System.out.println(message.getPayload());
	}
}