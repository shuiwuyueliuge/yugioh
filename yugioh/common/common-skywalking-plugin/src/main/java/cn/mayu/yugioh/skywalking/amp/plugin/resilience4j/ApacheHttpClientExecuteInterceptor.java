package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j;

import feign.Request;
import feign.Response;
import feign.httpclient.ApacheHttpClient;
import org.apache.skywalking.apm.agent.core.context.CarrierItem;
import org.apache.skywalking.apm.agent.core.context.ContextCarrier;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.ContextSnapshot;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;
import org.apache.skywalking.apm.util.StringUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import static feign.Util.valuesOrEmpty;

/**
 * @description: {@link ApacheHttpClient} 加入到span
 * @author: YgoPlayer
 * @time: 2021/7/6 1:41 下午
 */
public class ApacheHttpClientExecuteInterceptor implements InstanceMethodsAroundInterceptor {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static Field FIELD_HEADERS_OF_REQUEST;

    static {
        try {
            final Field field = Request.class.getDeclaredField("headers");
            field.setAccessible(true);
            FIELD_HEADERS_OF_REQUEST = field;
        } catch (Exception ignore) {
            FIELD_HEADERS_OF_REQUEST = null;
        }
    }

    @Override
    public void beforeMethod(EnhancedInstance enhancedInstance, Method method, Object[] allArguments, Class<?>[] classes, MethodInterceptResult methodInterceptResult) throws Throwable {
        Request request = (Request) allArguments[0];
        URL url = new URL(request.url());
        ContextCarrier contextCarrier = new ContextCarrier();
        int port = url.getPort() == -1 ? 80 : url.getPort();
        String remotePeer = url.getHost() + ":" + port;
        String operationName = url.getPath();
        FeignResolvedURL feignResolvedURL = PathVarInterceptor.URL_CONTEXT.get();
        if (feignResolvedURL != null) {
            try {
                operationName = operationName.replace(feignResolvedURL.getUrl(), feignResolvedURL.getOriginUrl());
            } finally {
                PathVarInterceptor.URL_CONTEXT.remove();
            }
        }
        if (operationName.length() == 0) {
            operationName = "/";
        }

        AbstractSpan span = ContextManager.createExitSpan(operationName, contextCarrier, remotePeer);
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!Objects.isNull(requestAttributes)) {
            ContextSnapshot snapshot = (ContextSnapshot) requestAttributes.getAttribute("circuitBreakerContextSnapshot", 10000);
            if (!Objects.isNull(snapshot)) {
                requestAttributes.removeAttribute("circuitBreakerContextSnapshot", 10000);
                ContextManager.continued(snapshot);
            }
        }

        span.setComponent(ComponentsDefine.FEIGN);
        Tags.HTTP.METHOD.set(span, request.httpMethod().name());
        Tags.URL.set(span, request.url());
        SpanLayer.asHttp(span);

        if (FeignPluginConfig.Plugin.Feign.COLLECT_REQUEST_BODY) {
            boolean needCollectHttpBody = false;
            Iterator<String> stringIterator = valuesOrEmpty(request.headers(), CONTENT_TYPE_HEADER).iterator();
            String contentTypeHeaderValue = stringIterator.hasNext() ? stringIterator.next() : "";
            for (String contentType : FeignPluginConfig.Plugin.Feign.SUPPORTED_CONTENT_TYPES_PREFIX.split(",")) {
                if (contentTypeHeaderValue.startsWith(contentType)) {
                    needCollectHttpBody = true;
                    break;
                }
            }
            if (needCollectHttpBody) {
                collectHttpBody(request, span);
            }
        }

        if (FIELD_HEADERS_OF_REQUEST != null) {
            Map<String, Collection<String>> headers = new LinkedHashMap<String, Collection<String>>();
            CarrierItem next = contextCarrier.items();
            while (next.hasNext()) {
                next = next.next();
                List<String> contextCollection = new ArrayList<String>(1);
                contextCollection.add(next.getHeadValue());
                headers.put(next.getHeadKey(), contextCollection);
            }
            headers.putAll(request.headers());

            FIELD_HEADERS_OF_REQUEST.set(request, Collections.unmodifiableMap(headers));
        }
    }

    private void collectHttpBody(final Request request, final AbstractSpan span) {
        if (request.body() == null || request.charset() == null) {
            return;
        }
        String tagValue = new String(request.body(), request.charset());
        tagValue = FeignPluginConfig.Plugin.Feign.FILTER_LENGTH_LIMIT > 0 ?
                StringUtil.cut(tagValue, FeignPluginConfig.Plugin.Feign.FILTER_LENGTH_LIMIT) : tagValue;

        Tags.HTTP.BODY.set(span, tagValue);
    }


    @Override
    public Object afterMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) throws Throwable {
        Response response = (Response) o;
        if (response != null) {
            int statusCode = response.status();
            AbstractSpan span = ContextManager.activeSpan();
            if (statusCode >= 400) {
                span.errorOccurred();
                Tags.STATUS_CODE.set(span, Integer.toString(statusCode));
            }
        }

        ContextManager.stopSpan();
        return o;
    }

    @Override
    public void handleMethodException(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {
        ContextManager.activeSpan().log(throwable);
    }
}
