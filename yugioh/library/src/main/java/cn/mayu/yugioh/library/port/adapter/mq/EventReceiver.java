package cn.mayu.yugioh.library.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.library.application.card.CardCreateCommandService;
import cn.mayu.yugioh.library.application.card.command.CardCreateCommand;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(EventInputStream.class)
public class EventReceiver {

	@Autowired
	private CardCreateCommandService cardCreateCommandService;

	@StreamListener(EventInputStream.EVENT_SAVE_INPUT)
	public void receiveSave(Message<String> message) throws Exception {
		String payload = getMqData(message);
		DomainEvent domainEvent = JsonParser.defaultInstance().readObjectValue(payload, DomainEvent.class);
		if ("card".equals(domainEvent.getType())) {
			CardCreateCommand cardCreateCommand = JsonParser.builder().failOnUnknownProperties(false).build().readObjectValue(domainEvent.getBody().toString(), CardCreateCommand.class);
			cardCreateCommandService.createCard(cardCreateCommand);
		}
	}

	private String getMqData(Message<String> message) throws Exception {
		Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag, false);
		return message.getPayload();
	}
}