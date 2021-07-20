package cn.mayu.yugioh.basic.gateway.daedalus.config.auth;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Objects;

/**
 * @description: oauth2 resources server 配置
 * @author: YgoPlayer
 * @time: 2021/6/29 7:26 下午
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        http.authorizeExchange(exchange -> exchange.anyExchange().access(authorizationManager()))
                .csrf()
                .disable();
        return http.build();
    }

    /**
     * 授权管理，可以通过其他途径获取用户权限数据进行验证
     */
    private ReactiveAuthorizationManager<AuthorizationContext> authorizationManager() {
        return (mono, authorizationContext) -> {
            ServerHttpRequest request = authorizationContext.getExchange().getRequest();

            if (Objects.equals("/favicon.ico", request.getPath().value())) {
                return Mono.just(new AuthorizationDecision(true));
            }

            List<String> authorities = Lists.newLinkedList(); // 用户权限信息
            return mono
                    .filter(Authentication::isAuthenticated)
                    .flatMapIterable(Authentication::getAuthorities)
                    .map(GrantedAuthority::getAuthority)
                    .any(authorities::contains)
                    .map(AuthorizationDecision::new)
                    .defaultIfEmpty(new AuthorizationDecision(false));
        };
    }

    /**
     * 改写webclient，添加负载均衡配置，从用户中心获取公钥
     * jwk-set-uri: lb://authorization/.well-known/jwks.json
     *
     * @param properties 主要提供jwk-set-uri
     * @param loadBalancedExchangeFilterFunction ReactorLoadBalancerExchangeFilterFunction
     * @return jwt decoder
     */
    @Bean
    @ConditionalOnProperty(name = "spring.security.oauth2.resourceserver.jwt.jwk-set-uri")
    ReactiveJwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties, LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction) {
        NimbusReactiveJwtDecoder nimbusReactiveJwtDecoder = NimbusReactiveJwtDecoder
                .withJwkSetUri(properties.getJwt().getJwkSetUri())
                .jwsAlgorithm(SignatureAlgorithm.from(properties.getJwt().getJwsAlgorithm()))
                .webClient(WebClient.builder().filter(loadBalancedExchangeFilterFunction).build())
                .build();
        String issuerUri = properties.getJwt().getIssuerUri();
        if (issuerUri != null) {
            nimbusReactiveJwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuerUri));
        }

        return nimbusReactiveJwtDecoder;
    }
}

