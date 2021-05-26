package cn.mayu.yugioh.prometheus.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.prometheus.port.adapter.stomp.user.ServerInfoProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

/**
 * @description: 接收mq的数据返回给客户端
 * @author: YgoPlayer
 * @time: 2021/5/17 4:37 下午
 */
@Component
public class TaskMsg implements Consumer<Message<RemoteDomainEvent>> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ServerInfoProvider infoProvider;

    @Override
    public void accept(Message<RemoteDomainEvent> message) {
        RemoteDomainEvent domainEvent = message.getPayload();
        System.out.println(domainEvent.getPayload());
        messagingTemplate.convertAndSendToUser(
                infoProvider.getServerName(),
                infoProvider.getDestination(),
                domainEvent.getPayload()
        );
    }
}
