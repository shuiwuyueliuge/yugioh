package cn.mayu.yugioh.library.port.adapter.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(CardMetaInputStream.class)
public class CardMetaEventReceiver {

	@Autowired
	private MqDataThreadPool mqDataThreadPool;

	@StreamListener(CardMetaInputStream.CARD_META_INPUT)
	public void receiveSave(Message<String> message) {
		mqDataThreadPool.put(message);
	}
}