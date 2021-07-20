package com.mayu.yugioh.common.web.reactive.handler;

import com.mayu.yugioh.common.web.reactive.expection.BaseException;
import com.mayu.yugioh.common.web.reactive.trace.TraceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(annotations = {RestWrapController.class})
public class WebFluxExceptionAdvice {

    private final int exceptionCode;

    private final String exceptionMsg;

    public WebFluxExceptionAdvice(RestCodeGenerator restCodeGenerator) {
        this.exceptionCode = restCodeGenerator.restSystemErrorCode();
        this.exceptionMsg = restCodeGenerator.restSystemErrorMsg();
    }

    /**
     * 处理自定义业务异常
     *
     * @param baseException 业务异常
     * @return 请求结果
     */
    @ExceptionHandler(BaseException.class)
    public Mono<RestResult> exception(BaseException baseException) {
        return TraceManager.contextTraceId().map(traceId -> {
            log.info("TraceId: [{}] Has a Business Error: {}", traceId, baseException.getRestMsg());
            return new RestResult(baseException.getCode(), baseException.getRestMsg());
        });
    }

    /**
     * 系统异常
     *
     * @param throwable 不确定的异常
     * @return 请求结果
     */
    @ExceptionHandler(Throwable.class)
    public Mono<RestResult> exception(Throwable throwable) {
        return TraceManager.contextTraceId().map(traceId -> {
            String msgTemplate = "TraceId: [%s] Has a System Error";
            String errorMsg = String.format(msgTemplate, traceId);
            log.error(errorMsg, throwable);
            return new RestResult(exceptionCode, exceptionMsg);
        });
    }
}