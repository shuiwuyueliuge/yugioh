package cn.mayu.yugioh.prometheus.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.prometheus.port.adapter.netty.ChannelSupervise;
import cn.mayu.yugioh.prometheus.port.adapter.stomp.user.ServerInfoProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @description: 接收mq的数据返回给客户端
 * @author: YgoPlayer
 * @time: 2021/5/17 4:37 下午
 */
@Slf4j
@Component
public class TaskMsg implements Consumer<Message<RemoteDomainEvent>> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ServerInfoProvider infoProvider;

    @Override
    public void accept(Message<RemoteDomainEvent> message) {
        RemoteDomainEvent domainEvent = message.getPayload();
        if (log.isDebugEnabled()) {
            log.debug("task message: {}", message.getPayload());
        }

        String routingKey = domainEvent.getRoutingKey();
        if (Objects.isNull(routingKey) || !routingKey.contains("|")) {
            return;
        }

        String serverName = routingKey.split("\\|")[0];
        String channelId = routingKey.split("\\|")[1];
        if (Objects.equals(serverName, infoProvider.getServerName())) {
            ChannelSupervise.send2One(channelId, domainEvent.getPayload());
            return;
        }

        messagingTemplate.convertAndSendToUser(
                infoProvider.getServerName(),
                infoProvider.getDestination(),
                domainEvent
        );
    }
}
