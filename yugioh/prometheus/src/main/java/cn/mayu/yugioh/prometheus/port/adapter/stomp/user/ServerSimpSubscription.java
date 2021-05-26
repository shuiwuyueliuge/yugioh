package cn.mayu.yugioh.prometheus.port.adapter.stomp.user;

import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;

/**
 * @description: server的订阅信息
 * @author: YgoPlayer
 * @time: 2021/5/26 2:35 下午
 */
public class ServerSimpSubscription implements SimpSubscription {

    private final String destination;

    private SimpSession simpSession;

    private final String id;

    public ServerSimpSubscription(String destination, String id) {
        this.destination = destination;
        this.id = id;
    }

    public void setSimpSession(SimpSession simpSession) {
        this.simpSession = simpSession;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public SimpSession getSession() {
        return simpSession;
    }

    @Override
    public String getDestination() {
        return destination;
    }
}
