package cn.mayu.yugioh.prometheus.port.adapter.stomp.user;

import com.google.common.collect.Sets;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import java.security.Principal;
import java.util.Set;

/**
 * @description: stomp用户数据，固定返回
 * @author: YgoPlayer
 * @time: 2021/5/26 2:15 下午
 */
public class ServerSimpUser implements SimpUser {

    private SimpSession simpSession;

    private final String serverName;

    public ServerSimpUser(String serverName) {
        this.serverName = serverName;
    }

    public void setSimpSession(SimpSession simpSession) {
        this.simpSession = simpSession;
    }

    @Override
    public String getName() {
        return serverName;
    }

    @Override
    public Principal getPrincipal() {
        return () -> serverName;
    }

    @Override
    public boolean hasSessions() {
        return true;
    }

    @Override
    public SimpSession getSession(String s) {
        return simpSession;
    }

    @Override
    public Set<SimpSession> getSessions() {
        return Sets.newHashSet(simpSession);
    }
}
