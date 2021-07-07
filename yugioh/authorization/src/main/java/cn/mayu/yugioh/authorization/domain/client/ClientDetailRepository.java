package cn.mayu.yugioh.authorization.domain.client;

import cn.mayu.yugioh.common.security.application.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface ClientDetailRepository extends ClientDetailsService, ClientDetailsServiceBuilder {
}
