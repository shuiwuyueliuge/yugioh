package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.StringTag;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description: 拦截{@link Resilience4JCircuitBreaker#run} 添加span
 * @author: YgoPlayer
 * @time: 2021/7/6 10:08 上午
 */
@Slf4j
public class CircuitBreakerRunInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, MethodInterceptResult methodInterceptResult) throws Throwable {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!Objects.isNull(requestAttributes)) {
            requestAttributes.setAttribute("circuitBreakerContextSnapshot", ContextManager.capture(), 10000);
        } else {
            log.info("Resilience4JCircuitBreaker#run no requestAttributes from RequestContextHolder");
        }

        CircuitBreakerInfoCache infoCache = (CircuitBreakerInfoCache) enhancedInstance.getSkyWalkingDynamicField();
        CircuitBreakerConfig config = infoCache.getCircuitBreakerConfig();
        AbstractSpan activeSpan = ContextManager.createLocalSpan("Resilience4JCircuitBreaker/run");
        activeSpan.setComponent(ComponentsDefine.FEIGN);
        activeSpan.setLayer(SpanLayer.RPC_FRAMEWORK);
        activeSpan.tag(new StringTag("feignGroup"), infoCache.getId());
        activeSpan.tag(new StringTag("failureRateThreshold"), String.valueOf(config.getFailureRateThreshold()));
        activeSpan.tag(new StringTag("permittedNumberOfCallsInHalfOpenState"), String.valueOf(config.getPermittedNumberOfCallsInHalfOpenState()));
        activeSpan.tag(new StringTag("slidingWindowSize"), String.valueOf(config.getSlidingWindowSize()));
        activeSpan.tag(new StringTag("slidingWindowType"), String.valueOf(config.getSlidingWindowType()));
        activeSpan.tag(new StringTag("minimumNumberOfCalls"), String.valueOf(config.getMinimumNumberOfCalls()));
        activeSpan.tag(new StringTag("writableStackTraceEnabled"), String.valueOf(config.isWritableStackTraceEnabled()));
        activeSpan.tag(new StringTag("automaticTransitionFromOpenToHalfOpenEnabled"), String.valueOf(config.isAutomaticTransitionFromOpenToHalfOpenEnabled()));
        activeSpan.tag(new StringTag("slowCallRateThreshold"), String.valueOf(config.getSlowCallRateThreshold()));
        activeSpan.tag(new StringTag("slowCallDurationThreshold"), String.valueOf(config.getSlowCallDurationThreshold()));
    }

    @Override
    public Object afterMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) throws Throwable {
        ContextManager.stopSpan();
        return o;
    }

    @Override
    public void handleMethodException(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {
        ContextManager.activeSpan().log(throwable);
    }
}