package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j;

import feign.RequestTemplate;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;

import java.lang.reflect.Method;

/**
 * {@link PathVarInterceptor} intercept the Feign RequestTemplate args resolve ;
 */
public class PathVarInterceptor implements InstanceMethodsAroundInterceptor {

    static final ThreadLocal<FeignResolvedURL> URL_CONTEXT = new ThreadLocal<FeignResolvedURL>();

    /**
     * Get the {@link RequestTemplate#url()} before feign.ReflectiveFeign.BuildTemplateByResolvingArgs#resolve(Object[],
     * RequestTemplate, Map) put it into the {@link PathVarInterceptor#URL_CONTEXT}
     *
     * @param method intercept method
     * @param result change this result, if you want to truncate the method.
     */
    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                             MethodInterceptResult result) {
        RequestTemplate template = (RequestTemplate) allArguments[1];
        URL_CONTEXT.set(new FeignResolvedURL(template.url()));
    }

    /**
     * Get the resolved {@link RequestTemplate#url()} after feign.ReflectiveFeign.BuildTemplateByResolvingArgs#resolve(Object[],
     * RequestTemplate, Map) put it into the {@link PathVarInterceptor#URL_CONTEXT}
     *
     * @param method intercept method
     * @param ret    the method's original return value.
     * @return result without change
     */
    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                              Object ret) {
        RequestTemplate resolvedTemplate = (RequestTemplate) ret;
        URL_CONTEXT.get().setUrl(resolvedTemplate.url());
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments,
                                      Class<?>[] argumentsTypes, Throwable t) {
        if (URL_CONTEXT.get() != null) {
            URL_CONTEXT.remove();
        }
    }
}
