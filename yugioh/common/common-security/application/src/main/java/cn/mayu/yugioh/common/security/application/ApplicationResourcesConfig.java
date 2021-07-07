package cn.mayu.yugioh.common.security.application;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @description: 登陆相关配置
 * @author: YgoPlayer
 * @time: 2021/6/28 9:53 上午
 */
@EnableResourceServer
@AllArgsConstructor
public class ApplicationResourcesConfig extends ResourceServerConfigurerAdapter {

    private final ClientDetailsService clientDetailsService;

    private final AuthorizationServerTokenServices tokenServices;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AuthorizeRequestManager authorizeRequestManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        authorizeRequestManager.config(http.authorizeRequests());
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin().successHandler(new TokenAuthenticationSuccessHandler(clientDetailsService, tokenServices));
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }
}
