package cn.mayu.yugioh.common.rpc;

import cn.mayu.yugioh.common.web.handler.RestCodeGenerator;
import cn.mayu.yugioh.common.web.trace.TraceManager;
import feign.Retryer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import java.util.Objects;

/**
 * @description: feign相关类自动配置
 * 目前feign不支持reactive，所以webflux使用的时候就像调用jdbc一样阻塞直到请求返回才响应结果{@link FeignBlockingLoadBalancerClient}。
 * feign的使用默认没有使用长链接，所以引入httpclient配置长链接{@link FeignHttpClientProperties}
 * feign failover配置{@link Retryer}
 * 超时，限流，熔断，隔离，降级
 * @author: YgoPlayer
 * @time: 2021/6/1 1:27 下午
 */
public class FeignConfiguration {

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public FeignDecoder feignDecoder(RestCodeGenerator restCodeGenerator) {
        return new FeignDecoder(restCodeGenerator);
    }

    /**
     * openfeign的重试配置，failover机制.第一次请求宕机第二次重新选择服务提供者执行
     *
     * @return 重试配置
     */
    @Bean
    public Retryer retryer(FeignProperties feignProperties) {
        if (Objects.isNull(feignProperties.getRetry()) || !feignProperties.getRetry().check()) {
            return Retryer.NEVER_RETRY;
        }

        return new Retryer.Default(
                feignProperties.getRetry().getPeriod(),
                feignProperties.getRetry().getMaxPeriod(),
                feignProperties.getRetry().getMaxAttempts()
        );
    }

    @Bean
    public FeignTraceInterceptor requestInterceptor(TraceManager traceManager) {
        return new FeignTraceInterceptor(traceManager);
    }

    @Bean
    public FeignExceptionAdvice feignExceptionAdvice(TraceManager traceManager) {
        return new FeignExceptionAdvice(traceManager);
    }
}
