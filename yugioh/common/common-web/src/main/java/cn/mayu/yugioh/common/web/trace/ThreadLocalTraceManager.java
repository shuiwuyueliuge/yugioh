package cn.mayu.yugioh.common.web.trace;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * @description: trace id 管理
 * @author: YgoPlayer
 * @time: 2021/6/10 9:19 上午
 */
public class ThreadLocalTraceManager implements TraceManager {

    private static final ThreadLocal<String> STRING_THREAD_LOCAL = new InheritableThreadLocal<>();

    @Override
    public String getTraceIdFromRequest(HttpServletRequest request) {
        String traceId = request.getHeader(ThreadLocalTraceManager.TRACE_ID);
        if (Objects.isNull(traceId)) {
            traceId = String.join("", UUID.randomUUID().toString().split("-"));
        }

        STRING_THREAD_LOCAL.set(traceId);
        return traceId;
    }

    @Override
    public String getCurrentTraceId() {
        return STRING_THREAD_LOCAL.get();
    }
}
