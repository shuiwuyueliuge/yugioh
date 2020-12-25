package cn.mayu.yugioh.library.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.library.application.card.CardCommandService;
import cn.mayu.yugioh.library.application.card.command.CardCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(CardMetaInputStream.class)
public class CardMetaEventReceiver {

	@Autowired
	private CardCommandService cardCommandService;

	@StreamListener(CardMetaInputStream.CARD_META_INPUT)
	public void receiveSave(Message<String> message) {
		String payload = message.getPayload();
		RemoteDomainEvent domainEvent = JsonParser.defaultInstance().readObjectValue(payload, RemoteDomainEvent.class);
		CardCreateCommand cardCreateCommand = JsonParser.builder().failOnUnknownProperties(false).build()
				.readObjectValue(domainEvent.getPayload(), CardCreateCommand.class);
		cardCommandService.createCard(cardCreateCommand);
	}
}