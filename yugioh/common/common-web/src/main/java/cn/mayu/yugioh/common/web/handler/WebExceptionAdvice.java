package cn.mayu.yugioh.common.web.handler;

import cn.mayu.yugioh.common.web.expection.BaseException;
import cn.mayu.yugioh.common.web.trace.ThreadLocalTraceManager;
import cn.mayu.yugioh.common.web.trace.TraceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(annotations = {RestWrapController.class})
public class WebExceptionAdvice {

    private final int exceptionCode;

    private final String exceptionMsg;

    private final TraceManager traceManager;

    public WebExceptionAdvice(RestCodeGenerator restCodeGenerator, TraceManager traceManager) {
        this.exceptionCode = restCodeGenerator.restSystemErrorCode();
        this.exceptionMsg = restCodeGenerator.restSystemErrorMsg();
        this.traceManager = traceManager;
    }

    /**
     * 处理自定义业务异常
     *
     * @param baseException 业务异常
     * @return 请求结果
     */
    @ExceptionHandler(BaseException.class)
    public RestResult exception(BaseException baseException) {
        String traceId = traceManager.getCurrentTraceId();
        log.info("TraceId: [{}] Has a Business Error: {}", traceId, baseException.getRestMsg());
        return new RestResult(baseException.getCode(), baseException.getRestMsg());
    }

    /**
     * 系统异常
     *
     * @param throwable 不确定的异常
     * @return 请求结果
     */
    @ExceptionHandler(Throwable.class)
    public RestResult exception(Throwable throwable) {
        String traceId =  traceManager.getCurrentTraceId();
        String msgTemplate = "TraceId: [%s] Has a System Error";
        String errorMsg = String.format(msgTemplate, traceId);
        log.error(errorMsg, throwable);
        return new RestResult(exceptionCode, exceptionMsg);
    }
}