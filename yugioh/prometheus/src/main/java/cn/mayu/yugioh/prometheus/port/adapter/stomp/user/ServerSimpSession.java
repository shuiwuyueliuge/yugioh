package cn.mayu.yugioh.prometheus.port.adapter.stomp.user;

import com.google.common.collect.Sets;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import java.util.Set;

/**
 * @description: 服务的session id
 * @author: YgoPlayer
 * @time: 2021/5/26 2:19 下午
 */
public class ServerSimpSession implements SimpSession {

    private final String sessionId;

    private final SimpUser user;

    private SimpSubscription simpSubscription;

    public ServerSimpSession(String sessionId, SimpUser user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public void setSimpSubscription(SimpSubscription simpSubscription) {
        this.simpSubscription = simpSubscription;
    }

    @Override
    public String getId() {
        return sessionId;
    }

    @Override
    public SimpUser getUser() {
        return user;
    }

    @Override
    public Set<SimpSubscription> getSubscriptions() {
        return Sets.newHashSet(simpSubscription);
    }
}
