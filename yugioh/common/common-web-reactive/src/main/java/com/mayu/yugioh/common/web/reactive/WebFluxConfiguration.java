package com.mayu.yugioh.common.web.reactive;

import com.mayu.yugioh.common.web.reactive.handler.*;
import com.mayu.yugioh.common.web.reactive.trace.WebFluxTraceFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

/**
 * @description: webflux相关类自动配置
 * @author: YgoPlayer
 * @time: 2021/6/1 1:27 下午
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxConfiguration {

    @Bean
    public WebFluxTraceFilter webFluxTraceFilter() {
        return new WebFluxTraceFilter();
    }

    @Bean
    public FilterBodyHandler filterBodyHandler() {
        return new FilterBodyHandler();
    }

    @Bean
    @ConditionalOnMissingBean(RestCodeGenerator.class)
    public RestCodeGenerator restCodeGenerator() {
        return new DefaultRestCodeGenerator();
    }

    @Bean
    public WebFluxExceptionAdvice webFluxExceptionAdvice(RestCodeGenerator restCodeGenerator) {
        return new WebFluxExceptionAdvice(restCodeGenerator);
    }

    @Bean
    public WebFluxResultHandler webFluxResultHandler(
            @Qualifier("webFluxAdapterRegistry") ReactiveAdapterRegistry reactiveAdapterRegistry,
            ServerCodecConfigurer serverCodecConfigurer,
            @Qualifier("webFluxContentTypeResolver") RequestedContentTypeResolver contentTypeResolver,
            RestCodeGenerator restCodeGenerator
    ) {
        return new WebFluxResultHandler(reactiveAdapterRegistry, serverCodecConfigurer, contentTypeResolver, restCodeGenerator);
    }
}
