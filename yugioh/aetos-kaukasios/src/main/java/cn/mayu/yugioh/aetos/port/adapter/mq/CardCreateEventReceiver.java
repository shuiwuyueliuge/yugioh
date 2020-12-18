package cn.mayu.yugioh.aetos.port.adapter.mq;

import cn.mayu.yugioh.aetos.application.CardCommandService;
import cn.mayu.yugioh.aetos.application.CardCreateCommand;
import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import java.io.IOException;

@EnableBinding(CardCreateInputStream.class)
public class CardCreateEventReceiver {

	@Autowired
	private CardCommandService cardCommandService;

	@StreamListener(CardCreateInputStream.CARD_CREATE_INPUT)
	public void receiveSave(Message<String> message) {
		DomainEvent domainEvent = JsonParser.defaultInstance().readObjectValue(message.getPayload(), DomainEvent.class);
		CardCreateCommand cardCreateCommand = JsonParser.builder().failOnUnknownProperties(false).build().readObjectValue(domainEvent.getBody().toString(), CardCreateCommand.class);
		cardCommandService.createCard(cardCreateCommand);
		basicAck(message);
	}

	private void basicAck(Message<String> message) {
		Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		try {
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}