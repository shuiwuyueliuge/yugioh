package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.NameMatch;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import static net.bytebuddy.matcher.ElementMatchers.any;

/**
 * @description: 对 {@link Resilience4JCircuitBreaker} 拦截增强
 * @author: YgoPlayer
 * @time: 2021/7/6 10:04 上午
 */
public class CircuitBreakerInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String CLASS_NAME = "org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker";

    private static final String RUN_METHOD = "run";

    @Override
    protected ClassMatch enhanceClass() {
        return NameMatch.byName(CLASS_NAME);
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[] {
                new ConstructorInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getConstructorMatcher() {
                        return any();
                    }

                    @Override
                    public String getConstructorInterceptor() {
                        return "cn.mayu.yugioh.skywalking.amp.plugin.resilience4j.CircuitBreakerConstructorInterceptor";
                    }
                }
        };
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return ElementMatchers.named(RUN_METHOD);
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return "cn.mayu.yugioh.skywalking.amp.plugin.resilience4j.CircuitBreakerRunInterceptor";
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }
}
