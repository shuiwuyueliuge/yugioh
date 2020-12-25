package cn.mayu.yugioh.library.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.library.application.include.IncludeCommandService;
import cn.mayu.yugioh.library.application.include.command.IncludeCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(IncludeMetaInputStream.class)
public class IncludeMetaEventReceiver {

	@Autowired
	private IncludeCommandService includeCommandService;

	@StreamListener(IncludeMetaInputStream.INCLUDE_META_INPUT)
	public void receiveSave(Message<String> message) {
		String payload = message.getPayload();
		RemoteDomainEvent domainEvent = JsonParser.defaultInstance().readObjectValue(payload, RemoteDomainEvent.class);
		IncludeCreateCommand includeCreateCommand = JsonParser.builder()
				.failOnUnknownProperties(false).build().readObjectValue(domainEvent.getPayload(), IncludeCreateCommand.class);
		includeCommandService.createInclude(includeCreateCommand);
	}
}