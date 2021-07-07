package cn.mayu.yugioh.common.security.application;

import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface ClientDetailsServiceBuilder {

    ClientDetailsService build();
}
