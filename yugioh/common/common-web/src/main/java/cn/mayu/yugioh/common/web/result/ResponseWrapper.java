package cn.mayu.yugioh.common.web.result;

import cn.mayu.yugioh.common.web.expection.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.InvocableHandlerMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@ConditionalOnClass(name = {"org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler"})
@Aspect
public class ResponseWrapper {

    private final int successCode;

    private final int exceptionCode;

    private final String exceptionMsg;

    public ResponseWrapper(RestCodeGenerator restCodeGenerator) {
        this.successCode = restCodeGenerator.successCode();
        this.exceptionCode = restCodeGenerator.exceptionCode();
        this.exceptionMsg = restCodeGenerator.exceptionMsg();
    }

    @Around(value = "execution(* org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler.handleResult(..)) && args(exchange, result)", argNames = "point,exchange,result")
    public Object wrap(ProceedingJoinPoint point, ServerWebExchange exchange, HandlerResult result) throws Throwable {
        InvocableHandlerMethod handlerMethod = (InvocableHandlerMethod) result.getHandler();
        Class<?> beanType = handlerMethod.getBeanType();
        Object methodResult = beanType.isAnnotationPresent(RestControllerAdvice.class) ?
                handleException(result) : handleSuccess(result, beanType);
        return returnValue(point, exchange, result, methodResult);
    }

    private Object handleException(HandlerResult result) {
        Object returnValue = result.getReturnValue();
        BaseException baseException = (BaseException) returnValue;
        if (baseException.getSourceException() != null) { // 系统异常
            log.error("{}", baseException.getSourceException());
            return new RestResult(exceptionCode, exceptionMsg);
        } else { // 自定义业务异常
            log.info("{}", baseException.getRestMsg());
            return new RestResult(baseException.getCode(), baseException.getRestMsg());
        }
    }

    private Object handleSuccess(HandlerResult result, Class<?> beanType) {
        if (!beanType.isAnnotationPresent(ResultWrapper.class)) { // 不需要结果包装
            return null;
        }

        Object returnValue = result.getReturnValue();
        ResolvableType resolvableType = result.getReturnType();
        Class<?> returnTypeClass = resolvableType.resolve();
        if (Pageable.class == returnTypeClass) {
            return new PageRestResult(successCode, (Pageable<?>) returnValue);
        } else if (Mono.class == returnTypeClass) {
            return handleMono(resolvableType, ((Mono<?>) returnValue));
        } else if (Flux.class == returnTypeClass) {
            return handleMono(resolvableType, ((Flux<?>) returnValue).collectList());
        } else {
            return RestResult.thisOrString(returnTypeClass, successCode, returnValue);
        }
    }

    private Object handleMono(ResolvableType resolvableType, Mono<?> returnValue) {
        return returnValue.map(responseValue ->
                RestResult.thisOrString(resolvableType.getGeneric(0).resolve(), successCode, responseValue)
        );
    }

    private Object returnValue(ProceedingJoinPoint point, ServerWebExchange exchange, HandlerResult result, Object methodResult) throws Throwable {
        if (methodResult == null) {
            return point.proceed();
        }

        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HandlerResult handlerResult = new HandlerResult(result.getHandler(), methodResult, result.getReturnTypeSource());
        return point.proceed(new Object[]{exchange, handlerResult});
    }
}