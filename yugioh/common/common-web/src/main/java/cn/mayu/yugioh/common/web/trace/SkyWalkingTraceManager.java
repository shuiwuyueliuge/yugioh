package cn.mayu.yugioh.common.web.trace;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @description: trace id 管理
 * @author: YgoPlayer
 * @time: 2021/6/10 9:19 上午
 */
@Slf4j
public class SkyWalkingTraceManager implements TraceManager {

    /**
     * sw8: 1-YjdhZWIyMWNiNTVlNGZiNjg0ZDFlOTQ2MzlmZDgwNTQuMTAzLjE2MjU1NTIxMzEzNjUwMDAx-YjdhZWIyMWNiNTVlNGZiNjg0ZDFlOTQ2MzlmZDgwNTQuMTQ1LjE2MjU1NTIxMzEzNzUwMDAw-0-Y2VyZXM=-ZjI1ODM3ZDBjM2EzNDU0MzhiM2IxZGE2MTU3M2RkNmRAMTkyLjE2OC4wLjEwOQ==-L2V2ZW50L3JlY2VpdmU=-MTkyLjE2OC4wLjEwOToxMDEwMQ==
     *
     * 1.Sample. 0 or 1. 0 means context exists, but could(most likely will) ignore. 1 means this trace need to be sampled and send to backend.
     * 2.Trace Id. String(BASE64 encoded). Literal String and unique globally.
     * 3.Parent trace segment Id. String(BASE64 encoded). Literal String and unique globally.
     * 4.Parent span Id. Integer. Begin with 0. This span id points to the parent span in parent trace segment.
     * 5.Parent service. String(BASE64 encoded). The length should not be less or equal than 50 UTF-8 characters.
     * 6.Parent service instance. String(BASE64 encoded). The length should be less or equal than 50 UTF-8 characters.
     * 7.Parent endpoint. String(BASE64 encoded). Operation Name of the first entry span in the parent segment. The length should be less than 150 UTF-8 characters.
     * 8.Target address used at client side of this request. String(BASE64 encoded). The network address(not must be IP + port) used at client side to access this target service.
     *
     * @param request
     * @return
     */
    @Override
    public String getTraceIdFromRequest(HttpServletRequest request) {
        String traceId = null;
        Enumeration<String> headEnumeration = request.getHeaderNames();
        while(headEnumeration.hasMoreElements()) {
            String swHead = headEnumeration.nextElement();
            if (!swHead.contains("sw")) {
                continue;
            }

            String headValue = request.getHeader(swHead);
            if (!headValue.contains("-")) {
                continue;
            }

            String[] segments = headValue.split("-");
            if (segments.length != 8) {
                continue;
            }

            // parse sw8:header
            String traceIdBase64 = segments[1];
            try {
                byte[] traceIdByte = Base64.getDecoder().decode(traceIdBase64);
                traceId = new String(traceIdByte, StandardCharsets.UTF_8);
            } catch (IllegalArgumentException e) {
                log.error("parse sw8:header[" + traceIdBase64 + "] error", e);
            }
        }

        return Objects.isNull(traceId) ? TraceContext.traceId() : traceId;
    }

    @Override
    public String getCurrentTraceId() {
        return TraceContext.traceId();
    }
}
