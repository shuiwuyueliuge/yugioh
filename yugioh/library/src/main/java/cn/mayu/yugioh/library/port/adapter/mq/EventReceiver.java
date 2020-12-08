package cn.mayu.yugioh.library.port.adapter.mq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(EventInputStream.class)
public class EventReceiver {

	@StreamListener(EventInputStream.EVENT_SAVE_INPUT)
	public void receiveSave(Message<String> message) throws Exception {
		System.out.println(message);
	}
}
