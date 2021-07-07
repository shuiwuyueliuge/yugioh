package cn.mayu.yugioh.common.rpc;

import cn.mayu.yugioh.common.web.trace.TraceManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/9 12:22 下午
 */
public class FeignTraceInterceptor implements RequestInterceptor {

    private final TraceManager traceManager;

    public FeignTraceInterceptor(TraceManager traceManager) {
        this.traceManager = traceManager;
    }

    @Override
    public void apply(RequestTemplate template) {
        String traceId = traceManager.getCurrentTraceId();
        template.header(TraceManager.TRACE_ID, traceId);
    }
}
