package cn.mayu.yugioh.common.web.trace;

import javax.servlet.http.HttpServletRequest;

public interface TraceManager {

    String TRACE_ID = "trace-id";

    String getTraceIdFromRequest(HttpServletRequest request);

    String getCurrentTraceId();
}