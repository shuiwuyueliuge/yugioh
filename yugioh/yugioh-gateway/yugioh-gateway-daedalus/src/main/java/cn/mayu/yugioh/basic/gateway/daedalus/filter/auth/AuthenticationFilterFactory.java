package cn.mayu.yugioh.basic.gateway.daedalus.filter.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
public class AuthenticationFilterFactory extends AbstractGatewayFilterFactory<AuthenticationConfig> {

    public AuthenticationFilterFactory(){
        super(AuthenticationConfig.class);
    }

    @Override
    public String name() {
        return "Authentication";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("tokenType");
    }

    @Override
    public GatewayFilter apply(AuthenticationConfig config) {
        return config.getTokenType().getFilter();
    }
}
