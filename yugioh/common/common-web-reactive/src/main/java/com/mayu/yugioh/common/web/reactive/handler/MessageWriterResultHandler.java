package com.mayu.yugioh.common.web.reactive.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.HandlerResultHandler;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.AbstractMessageWriterResultHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @description: webflux中拦截controller返回的消息
 * @author: YgoPlayer
 * @time: 2021/5/31 3:49 下午
 */
@Slf4j
public abstract class MessageWriterResultHandler<T> extends AbstractMessageWriterResultHandler implements HandlerResultHandler {

    private final MethodParameter methodParameter;

    protected MessageWriterResultHandler(
            List<HttpMessageWriter<?>> messageWriters,
            RequestedContentTypeResolver contentTypeResolver,
            ReactiveAdapterRegistry adapterRegistry) {
        super(messageWriters, contentTypeResolver, adapterRegistry);
        setOrder(getOrderInt());
        methodParameter = this.initMethodParameter();
    }

    private MethodParameter initMethodParameter() {
        try {
            Method method = MessageWriterResultHandler.class.getDeclaredMethod("returnTypeSource");
            method.setAccessible(true);
            return new MethodParameter(method, -1);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private T returnTypeSource() {
        return getReturnTypeSource();
    }

    @Override
    public final boolean supports(HandlerResult handlerResult) {
        MethodParameter returnType = handlerResult.getReturnTypeSource();
        Class<?> containingClass = returnType.getContainingClass();
        return AnnotatedElementUtils.hasAnnotation(containingClass, getHandlerAnnotation()) ||
                returnType.hasMethodAnnotation(getHandlerAnnotation());
    }

    @Override
    public final Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Object returnValue = result.getReturnValue();
        ResolvableType resolvableType = result.getReturnType();
        Class<?> returnTypeClass = resolvableType.resolve();
        Mono<?> monoResult;
        if (!Objects.isNull(returnValue)) {
            monoResult = Mono.just(returnValue);
            if (Mono.class == returnTypeClass) {
                monoResult = (Mono<?>) returnValue;
            }

            if (Flux.class == returnTypeClass) {
                monoResult = ((Flux<?>) returnValue).collectList();
            }
        } else {
            monoResult = Mono.just("");
        }

        Mono<T> realResult = doHandleResult(monoResult, returnTypeClass);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HandlerResult handlerResult = new HandlerResult(result.getHandler(), realResult, methodParameter);
        Object body = handlerResult.getReturnValue();
        MethodParameter bodyTypeParameter = handlerResult.getReturnTypeSource();
        return writeBody(body, bodyTypeParameter, exchange);
    }

    /**
     * 处理指定注解的返回结果
     *
     * @return 指定注解
     */
    protected abstract Class<? extends Annotation> getHandlerAnnotation();

    /**
     * 值越小{@link DispatcherHandler}越容易被匹配到
     * {@link ResponseBodyResultHandler} order值为100，
     * 在{@link ResponseBodyResultHandler}之前拦截需要小于100
     *
     * @return 排序值
     */
    protected abstract int getOrderInt();

    /**
     * 处理结果
     *
     * @param monoResult 方法执行结果
     * @param returnTypeClass  方法结果类型
     * @return {@code Object} to indicate when request handling is complete.
     */
    public abstract Mono<T> doHandleResult(Mono monoResult, Class<?> returnTypeClass);

    /**
     * 获取指定的返回结果，返回类型和包装类型不匹配时候会报错
     *
     * @return 返回结果类型
     */
    protected abstract T getReturnTypeSource();
}
