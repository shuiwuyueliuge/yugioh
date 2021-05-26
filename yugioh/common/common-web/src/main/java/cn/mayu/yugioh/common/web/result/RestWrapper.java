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
import java.util.Objects;

@Slf4j
@ConditionalOnClass(name = {"org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler"})
@Aspect
public class RestWrapper {

    private final int successCode;

    private final int exceptionCode;

    private final String exceptionMsg;

    private final String successMsg;

    public RestWrapper(RestCodeGenerator restCodeGenerator) {
        this.successCode = restCodeGenerator.restSuccessCode();
        this.exceptionCode = restCodeGenerator.restSystemErrorCode();
        this.exceptionMsg = restCodeGenerator.restSystemErrorMsg();
        this.successMsg = restCodeGenerator.restSuccessMsg();
    }

    @Around(value = "execution(* org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler.handleResult(..)) && args(exchange, result)", argNames = "point,exchange,result")
    public Object wrap(ProceedingJoinPoint point, ServerWebExchange exchange, HandlerResult result) throws Throwable {
        InvocableHandlerMethod handlerMethod = (InvocableHandlerMethod) result.getHandler();
        Class<?> beanType = handlerMethod.getBeanType();
        Object methodResult = beanType.isAnnotationPresent(RestControllerAdvice.class) ?
                handleException(result, exchange) : handleSuccess(result, beanType);
        if (Objects.isNull(methodResult)) {
            return point.proceed();
        }

        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HandlerResult handlerResult = new HandlerResult(result.getHandler(), methodResult, result.getReturnTypeSource());
        return point.proceed(new Object[]{exchange, handlerResult});
    }

    private Object handleException(HandlerResult result, ServerWebExchange exchange) {
        Object returnValue = result.getReturnValue();
        if (Objects.isNull(returnValue)) {
            return new RestResult(successCode, successMsg);
        }

        BaseException baseException = (BaseException) returnValue;
        if (baseException.getSourceException() != null) { // 系统异常
            log.error("Request Method: [" + exchange.getRequest().getMethodValue() + "] Request URI: [" + exchange.getRequest().getPath() + "] Has a System Error",
                    baseException.getSourceException());
            return new RestResult(exceptionCode, exceptionMsg);
        } else { // 自定义业务异常
            log.info("Request Method: [{}] Request URI: [{}] Has a Business Error: {}",
                    exchange.getRequest().getMethodValue(), exchange.getRequest().getPath(), baseException.getRestMsg());
            return new RestResult(baseException.getCode(), baseException.getRestMsg());
        }
    }

    private Object handleSuccess(HandlerResult result, Class<?> beanType) {
        if (!beanType.isAnnotationPresent(ResponseBodyWrapper.class)) { // 不需要结果包装
            return null;
        }

        Object returnValue = result.getReturnValue();
        if (Objects.isNull(returnValue)) {
            return new RestResult(successCode, successMsg);
        }

        ResolvableType resolvableType = result.getReturnType();
        Class<?> returnTypeClass = resolvableType.resolve();
        if (Pageable.class == returnTypeClass) {
            return new PageRestResult(successCode, (Pageable<?>) returnValue);
        }

        if (Mono.class == returnTypeClass) {
            return handleMono(resolvableType, ((Mono<?>) returnValue));
        }

        if (Flux.class == returnTypeClass) {
            return handleMono(resolvableType, ((Flux<?>) returnValue).collectList());
        }

        return RestResult.thisOrString(returnTypeClass, successCode, returnValue);
    }

    private Object handleMono(ResolvableType resolvableType, Mono<?> returnValue) {
        return returnValue.map(responseValue ->
                RestResult.thisOrString(resolvableType.getGeneric(0).resolve(), successCode, responseValue)
        );
    }
}