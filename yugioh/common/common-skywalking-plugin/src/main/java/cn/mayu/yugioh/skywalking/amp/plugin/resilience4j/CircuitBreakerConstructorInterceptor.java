package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceConstructorInterceptor;

/**
 * @description: 收集{@link Resilience4JCircuitBreaker}参数
 * @author: YgoPlayer
 * @time: 2021/7/6 10:40 上午
 */
public class CircuitBreakerConstructorInterceptor implements InstanceConstructorInterceptor {

    @Override
    public void onConstruct(EnhancedInstance enhancedInstance, Object[] objects) {
        enhancedInstance.setSkyWalkingDynamicField(
                new CircuitBreakerInfoCache((String) objects[1], (CircuitBreakerConfig) objects[2]));
    }
}
