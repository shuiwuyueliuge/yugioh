package cn.mayu.yugioh.common.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/25 10:02 上午
 */
public class DefaultAuthorizeRequestManager implements AuthorizeRequestManager {

    @Autowired(required = false)
    private Set<Consumer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry>> authorizationConsumers;

    @Autowired(required = false)
    private Set<Consumer<WebSecurity.IgnoredRequestConfigurer>> ignoreConsumers;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        if (!Objects.isNull(authorizationConsumers)) {
            authorizationConsumers.forEach(consumer -> consumer.accept(registry));
        }

        registry.anyRequest().authenticated();
    }

    @Override
    public void config(WebSecurity.IgnoredRequestConfigurer ignoredRequestConfigurer) {
        if (!Objects.isNull(ignoreConsumers)) {
            ignoreConsumers.forEach(consumer -> consumer.accept(ignoredRequestConfigurer));
        }
    }
}
