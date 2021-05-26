package cn.mayu.yugioh.prometheus.port.adapter.stomp;

import cn.mayu.yugioh.prometheus.port.adapter.stomp.user.ServerInfoProvider;
import cn.mayu.yugioh.prometheus.port.adapter.stomp.user.ServerSimpUserRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Component;

/**
 * @description: stomp 事件监听
 * @author: YgoPlayer
 * @time: 2021/5/26 3:22 下午
 */
@Component
public class StompEventListener implements ApplicationListener<ApplicationEvent>, ApplicationContextAware {

    private final StompConnector connector;

    private final ServerSimpUserRegistry serverSimpUserRegistry;

    private final ServerInfoProvider serverInfoProvider;

    private ApplicationContext appContext;

    public StompEventListener(StompConnector connector,
                              ServerSimpUserRegistry serverSimpUserRegistry,
                              ServerInfoProvider serverInfoProvider) {
        this.connector = connector;
        this.serverSimpUserRegistry = serverSimpUserRegistry;
        this.serverInfoProvider = serverInfoProvider;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof BrokerAvailabilityEvent) {
            connector.conn();
            return;
        }

        if (applicationEvent instanceof BrokerConnectedEvent) {
            connector.sub();
            serverSimpUserRegistry.init(
                    serverInfoProvider.getSessionId(),
                    serverInfoProvider.getServerName(),
                    serverInfoProvider.getDestination()
            );

            appContext.publishEvent(new BrokerLinkFinishedEvent(this));
            return;
        }

        if (applicationEvent instanceof BrokerSubFinishedEvent) {
            connector.ping();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
