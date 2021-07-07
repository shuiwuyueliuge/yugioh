package cn.mayu.yugioh.common.security.browser;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @description: 浏览器安全相关配置
 * Browser完全是基于页面跳转的场景使用的，需要相应的模板去支持比如jsp｜thymeleaf 等，
 * 如果有根据返回数据去渲染页面的场景请使用Application的相关jar。
 * @author: YgoPlayer
 * @time: 2021/6/25 9:48 上午
 */
@EnableWebSecurity
@AllArgsConstructor
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApplicationContext applicationContext;

    private final AuthorizeRequestManager authorizeRequestManager;

    private final LoginProperty loginProperty;

    private final CsrfProperty csrfProperty;

    private final LogoutProperty logoutProperty;

    private final RememberMeProperty rememberMeProperty;

    private final UserDetailsService userDetailsService;

    private final InvalidSessionStrategy invalidSessionStrategy;

    private final SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    private final SessionProperty sessionProperty;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin(); // 允许frame
        authorizeRequestManager.config(http.authorizeRequests());
        loginConfigure(http.formLogin());
        logoutConfigure(http.logout());
        csrfConfigure(http.csrf());
        rememberMeConfigure(http);
        sessionConfigure(http.sessionManagement());
    }

    private void sessionConfigure(SessionManagementConfigurer<HttpSecurity> sessionManagement) {
        sessionManagement.invalidSessionStrategy(invalidSessionStrategy);// session无效的时候默认的策略是跳回到登陆页面
        // 并发登陆
        sessionManagement.maximumSessions(sessionProperty.getMaximumSessions())
                .maxSessionsPreventsLogin(sessionProperty.isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy); // session超时默认策略是跳回到登陆页面
    }

    private void rememberMeConfigure(HttpSecurity http) throws Exception {
        if (!rememberMeProperty.isEnable()) {
            return;
        }

        http.rememberMe().tokenRepository(applicationContext.getBean(PersistentTokenRepository.class))
                .tokenValiditySeconds(rememberMeProperty.getTokenValiditySeconds())
                .userDetailsService(userDetailsService);
    }

    private void logoutConfigure(LogoutConfigurer<HttpSecurity> logout) {
        logout.logoutUrl(logoutProperty.getLogoutUrl());
    }

    private void csrfConfigure(CsrfConfigurer<HttpSecurity> csrf) {
        if (csrfProperty.isCsrfDisable()) {
            csrf.disable();
            return;
        }

        if (!Objects.isNull(csrfProperty.getCsrfIgnoringAnt())) {
            String[] ants = csrfProperty.getCsrfIgnoringAnt().split(",");
            Stream.of(ants).forEach(ant -> csrf.ignoringAntMatchers(ants));
        }
    }

    private void loginConfigure(FormLoginConfigurer<HttpSecurity> formLogin) throws Exception {
        if (Objects.isNull(loginProperty.getLoginPage())) {
            formLogin.and().httpBasic();
            return;
        }

        formLogin.loginPage(loginProperty.getLoginPage())
                .loginProcessingUrl(loginProperty.getProcessingUrl())
                .usernameParameter(loginProperty.getUsernameParameter())
                .passwordParameter(loginProperty.getPasswordParameter());
        if (!Objects.isNull(loginProperty.getFailureForwardUrl())) {
            formLogin.failureForwardUrl(loginProperty.getFailureForwardUrl());
        }

        if (!Objects.isNull(loginProperty.getSuccessForwardUrl())) {
            formLogin.successForwardUrl(loginProperty.getSuccessForwardUrl());
        }
    }

    /**
     * ignore不经过权限认证的过滤器,HttpSecurity permitAll()经过相关过滤器
     *
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web) {
        authorizeRequestManager.config(web.ignoring());
    }
}
