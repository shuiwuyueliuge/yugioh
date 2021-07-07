package cn.mayu.yugioh.common.rpc;

import cn.mayu.yugioh.common.web.handler.RestResult;
import cn.mayu.yugioh.common.web.handler.RestWrapController;
import cn.mayu.yugioh.common.web.trace.TraceManager;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(annotations = {RestWrapController.class})
public class FeignExceptionAdvice {

    private final TraceManager traceManager;

    public FeignExceptionAdvice(TraceManager traceManager) {
        this.traceManager = traceManager;
    }

    /**
     * 处理feign业务异常
     *
     * @param feignException 业务异常
     * @return 请求结果
     */
    @ExceptionHandler(FeignException.class)
    public RestResult exception(FeignException feignException) {
        String traceId = traceManager.getCurrentTraceId();
        log.info("TraceId: [{}] Feign Error: {}", traceId, feignException.getMessage());
        return new RestResult(feignException.status(), feignException.getMessage());
    }
}