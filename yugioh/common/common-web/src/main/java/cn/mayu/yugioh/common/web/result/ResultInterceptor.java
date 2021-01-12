package cn.mayu.yugioh.common.web.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.lang.reflect.Method;
import static cn.mayu.yugioh.common.web.result.ResultWrapper.RESPONSE_BODY_ANN;

public class ResultInterceptor implements WebFilter {

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return webFilterChain.filter(serverWebExchange).contextWrite(ctx -> {
            this.handlerMapping.getHandlerInternal(serverWebExchange).subscribe(handlerMethod -> {
                final Method method = handlerMethod.getMethod();
                final Class<?> clazz = handlerMethod.getBeanType();
                if (clazz.isAnnotationPresent(ResultWrapper.class) || method.isAnnotationPresent(ResultWrapper.class)) {
                    ctx.put(RESPONSE_BODY_ANN, clazz.getAnnotation(ResultWrapper.class));
                }
            });

            return ctx;
        });
    }
}