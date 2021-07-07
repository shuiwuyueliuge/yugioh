package cn.mayu.yugioh.common.web;

import cn.mayu.yugioh.common.web.handler.DefaultRestCodeGenerator;
import cn.mayu.yugioh.common.web.handler.RestCodeGenerator;
import cn.mayu.yugioh.common.web.handler.WebExceptionAdvice;
import cn.mayu.yugioh.common.web.handler.WebResultHandler;
import cn.mayu.yugioh.common.web.trace.SkyWalkingTraceManager;
import cn.mayu.yugioh.common.web.trace.ThreadLocalTraceManager;
import cn.mayu.yugioh.common.web.trace.TraceManager;
import cn.mayu.yugioh.common.web.trace.WebTraceFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: webflux相关类自动配置
 * @author: YgoPlayer
 * @time: 2021/6/1 1:27 下午
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnProperty(prefix = "web.trace", name = "type", havingValue = "skywalking")
    public TraceManager skyWalkingTraceManager() {
        return new SkyWalkingTraceManager();
    }

    @Bean
    @ConditionalOnMissingBean(RestCodeGenerator.class)
    public RestCodeGenerator restCodeGenerator() {
        return new DefaultRestCodeGenerator();
    }

    @Bean
    public WebExceptionAdvice webExceptionAdvice(RestCodeGenerator restCodeGenerator, TraceManager traceManager) {
        return new WebExceptionAdvice(restCodeGenerator, traceManager);
    }

    @Bean
    public WebResultHandler webResultHandler(RestCodeGenerator restCodeGenerator) {
        return new WebResultHandler(restCodeGenerator);
    }

    /**
     * WebFluxResultHandler 处理返回string类型
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
         converters.add(0, new MappingJackson2HttpMessageConverter());
    }

    @Bean
    public FilterRegistrationBean<WebTraceFilter> webTraceFilterFilterRegistrationBean(TraceManager traceManager) {
        FilterRegistrationBean<WebTraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WebTraceFilter(traceManager));
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    @ConditionalOnMissingBean(TraceManager.class)
    public TraceManager traceManager() {
        return new ThreadLocalTraceManager();
    }
}
