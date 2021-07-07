package cn.mayu.yugioh.basic.gateway.daedalus.config;

import com.google.common.collect.Lists;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
// import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;

/**
 * @description: 授权管理，可以通过其他途径获取用户权限数据进行验证
 * @author: YgoPlayer
 * @time: 2021/6/29 7:28 下午
 */
// @Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono,
                                             AuthorizationContext authorizationContext) {
        List<String> authorities = Lists.newLinkedList(); // 用户权限信息
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}