package cn.mayu.yugioh.prometheus.port.adapter.stomp;

import cn.mayu.yugioh.prometheus.port.adapter.stomp.user.ServerInfoProvider;
import cn.mayu.yugioh.prometheus.port.adapter.stomp.user.ServerSimpUserRegistry;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.SimpUserRegistry;

/**
 * @description: stomp链接配置
 * @author: YgoPlayer
 * @time: 2021/5/26 1:26 下午
 */
@Configuration
public class StompConfig extends AbstractMessageBrokerConfiguration {

    private final RabbitProperties rabbitProperties;

    private final ServerSimpUserRegistry serverSimpUserRegistry;

    private final ServerInfoProvider serverInfoProvider;

    public StompConfig(
            RabbitProperties rabbitProperties,
            ServerSimpUserRegistry serverSimpUserRegistry,
            ServerInfoProvider serverInfoProvider
    ) {
        this.rabbitProperties = rabbitProperties;
        this.serverSimpUserRegistry = serverSimpUserRegistry;
        this.serverInfoProvider = serverInfoProvider;
    }

    @Override
    protected SimpUserRegistry createLocalUserRegistry(Integer integer) {
        return serverSimpUserRegistry;
    }

    protected void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new DefaultChannelInterceptor(getApplicationContext()));
    }

    @Override
    protected void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/stomp") // 配置请求都以/app打头，没有特殊意义，例如：@MessageMapping("/hello")，其实真实路径是/app/hello
                .enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost(rabbitProperties.getHost())
                .setClientLogin(rabbitProperties.getUsername())
                .setClientPasscode(rabbitProperties.getPassword())
                .setSystemLogin(rabbitProperties.getUsername())
                .setSystemPasscode(rabbitProperties.getPassword())
                .setVirtualHost(rabbitProperties.getVirtualHost())
                .setAutoStartup(true)
                .setUserDestinationBroadcast(serverInfoProvider.getDestination())
                .setUserRegistryBroadcast(serverInfoProvider.getDestination());
    }
}
