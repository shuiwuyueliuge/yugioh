package cn.mayu.yugioh.prometheus.port.adapter.stomp.user;

import com.google.common.collect.Sets;
import org.springframework.messaging.simp.user.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * @description: stomp用户注册
 * @author: YgoPlayer
 * @time: 2021/5/26 2:13 下午
 */
@Component
public class ServerSimpUserRegistry implements SimpUserRegistry {

    private SimpUser user;

    private SimpSubscription simpSubscription;

    private int count = 0;

    public void init(String sessionId, String serverName, String destination) {
        ServerSimpUser user = new ServerSimpUser(serverName);
        ServerSimpSession simpSession = new ServerSimpSession(sessionId, user);
        user.setSimpSession(simpSession);
        ServerSimpSubscription subscription = new ServerSimpSubscription(serverName,destination);
        simpSession.setSimpSubscription(subscription);
        this.user = user;
        this.simpSubscription = subscription;
        this.count = 1;
    }

    @Override
    public SimpUser getUser(String s) {
        return user;
    }

    @Override
    public Set<SimpUser> getUsers() {
        if (Objects.isNull(user)) {
            return Sets.newHashSet();
        }

        return Sets.newHashSet(user);
    }

    @Override
    public int getUserCount() {
        return count;
    }

    @Override
    public Set<SimpSubscription> findSubscriptions(SimpSubscriptionMatcher simpSubscriptionMatcher) {
        return Sets.newHashSet(simpSubscription);
    }
}
