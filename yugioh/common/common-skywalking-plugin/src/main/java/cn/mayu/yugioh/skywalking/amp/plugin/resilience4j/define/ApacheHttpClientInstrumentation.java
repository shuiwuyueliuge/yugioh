package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j.define;

import feign.httpclient.ApacheHttpClient;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.NameMatch;

/**
 * @description: 对 {@link ApacheHttpClient} 可能在异步线程中执行
 * @author: YgoPlayer
 * @time: 2021/7/6 10:04 上午
 */
public class ApacheHttpClientInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String CLASS_NAME = "feign.httpclient.ApacheHttpClient";

    private static final String EXECUTE_METHOD = "execute";

    @Override
    protected ClassMatch enhanceClass() {
        return NameMatch.byName(CLASS_NAME);
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[0];
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return ElementMatchers.named(EXECUTE_METHOD);
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return "cn.mayu.yugioh.skywalking.amp.plugin.resilience4j.ApacheHttpClientExecuteInterceptor";
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }
}
