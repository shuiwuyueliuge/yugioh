package cn.mayu.yugioh.library.port.adapter.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(IncludeMetaInputStream.class)
public class IncludeMetaEventReceiver {

	@Autowired
	private MqDataThreadPool mqDataThreadPool;

	@StreamListener(IncludeMetaInputStream.INCLUDE_META_INPUT)
	public void receiveSave(Message<String> message) {
		mqDataThreadPool.put(message);
	}
}