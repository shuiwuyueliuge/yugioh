package cn.mayu.yugioh.prometheus.port.adapter.stomp;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.prometheus.port.adapter.netty.ChannelSupervise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: stomp的消息拦截
 * @author: YgoPlayer
 * @time: 2021/5/26 1:29 下午
 */
@Slf4j
public class DefaultChannelInterceptor implements ChannelInterceptor {

    private final ApplicationContext appContext;

    public DefaultChannelInterceptor(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Assert.state(headerAccessor != null, "No StompHeaderAccessor");
        StompCommand command = headerAccessor.getCommand();
        SimpMessageType type = (SimpMessageType) message.getHeaders().get("simpMessageType");
        if (Objects.equals(type, SimpMessageType.HEARTBEAT)) {
            appContext.publishEvent(new BrokerSubFinishedEvent(this));
            return;
        }

        if (Objects.equals(command, StompCommand.CONNECTED)) {
            appContext.publishEvent(new BrokerConnectedEvent(this));
        }

        if (Objects.equals(command, StompCommand.MESSAGE)) {
            RemoteDomainEvent domainEvent = JsonParser.defaultInstance().readObjectValue(message.getPayload().toString(), RemoteDomainEvent.class);
            String routingKey = domainEvent.getRoutingKey();
            if (!Objects.isNull(routingKey) && routingKey.contains("|")) {
                String channelId = routingKey.split("\\|")[1];
                ChannelSupervise.send2One(channelId, domainEvent.getPayload());
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("\npayload: {}\nheader:\n{}\n", new String((byte[]) message.getPayload()),
                    message.getHeaders().entrySet()
                            .stream()
                            .map(data -> data.getKey() + " -> " + data.getValue())
                            .collect(Collectors.joining("\n")));
        }


        ChannelInterceptor.super.postSend(message, channel, sent);
    }
}
