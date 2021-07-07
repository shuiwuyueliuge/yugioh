package cn.mayu.yugioh.common.security.browser;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @description: Browser自动配置
 * @author: YgoPlayer
 * @time: 2021/6/25 10:13 上午
 */
public class BrowserAutoConfiguration {

    @Bean
    public Consumer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> loginRegister(LoginProperty loginProperty) {
        return registry -> {
            if (!Objects.isNull(loginProperty.getLoginPage())) {
                registry.antMatchers(loginProperty.getLoginPage()).permitAll();
            }
        };
    }

    @Bean
    public Consumer<WebSecurity.IgnoredRequestConfigurer> staticResourcesIgnore() {
        return ignore -> ignore.antMatchers("/**/*.css")
                .antMatchers("/**/*.js")
                .antMatchers("/**/*.png")
                .antMatchers("/**/*.woff")
                .antMatchers("/**/*.ttf")
                .antMatchers("/**/*.map")
                .antMatchers("/**/*.html");
    }

    @Bean
    public AuthorizeRequestManager authorizeRequestManager() {
        return new DefaultAuthorizeRequestManager();
    }

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(LoginProperty loginProperty) {
        return new SimpleRedirectInvalidSessionStrategy(Objects.isNull(loginProperty.getLoginPage()) ? "/login" : loginProperty.getLoginPage());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(LoginProperty loginProperty) {
        return new SimpleRedirectSessionInformationExpiredStrategy(Objects.isNull(loginProperty.getLoginPage()) ? "/login" : loginProperty.getLoginPage());
    }

    @Bean
    @Conditional(PersistentTokenRepositoryCondition.class)
    @ConditionalOnMissingBean(PersistentTokenRepository.class)
    public PersistentTokenRepository persistentTokenRepository () {
        return new InMemoryTokenRepositoryImpl();
    }

    private static class PersistentTokenRepositoryCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            BeanFactory beanFactory = context.getBeanFactory();
            if (Objects.isNull(beanFactory)) {
                return false;
            }

            Environment environment = beanFactory.getBean(Environment.class);
            Boolean enable = environment.getProperty("spring.security.browser.remember-me.enable", Boolean.class);
            return !Objects.isNull(enable) && enable;
        }
    }
}
