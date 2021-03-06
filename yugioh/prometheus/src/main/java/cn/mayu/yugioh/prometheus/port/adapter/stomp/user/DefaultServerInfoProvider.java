package cn.mayu.yugioh.prometheus.port.adapter.stomp.user;

import org.springframework.context.annotation.Configuration;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: 服务信息，stomp使用
 * @author: YgoPlayer
 * @time: 2021/5/26 2:07 下午
 */
@Configuration
public class DefaultServerInfoProvider implements ServerInfoProvider {

    private final String sessionId;

    private final String serverName;

    public DefaultServerInfoProvider() {
        this.sessionId = getUUID();
        this.serverName = getUUID();
    }

    private String getUUID() {
        return Stream.of(UUID.randomUUID().toString().split("-")).collect(Collectors.joining());
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public String getDestination() {
        return "/topic/prometheus";
    }
}
