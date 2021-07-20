package cn.mayu.yugioh.common.web.trace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 记录请求和响应数据
 * @author: YgoPlayer
 * @time: 2021/6/1 11:48 上午
 */
@Slf4j
public class WebTraceFilter extends OncePerRequestFilter {

    private final TraceManager traceManager;

    public WebTraceFilter(TraceManager traceManager) {
        this.traceManager = traceManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ReadHttpServletRequestWrapper requestWrapper = new ReadHttpServletRequestWrapper(request);
        String traceId = traceManager.getTraceIdFromRequest(request);
        requestLog(request, requestWrapper.getCachedBytes(), traceId);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        responseLog(response, responseWrapper.getContentAsByteArray(), traceId);
        responseWrapper.copyBodyToResponse();
    }

    private void responseLog(HttpServletResponse response, byte[] content, String traceId) {
        String bodyString = new String(content, StandardCharsets.UTF_8);
        String header = response.getHeaderNames().stream()
                .map(headerName -> headerName + ": " + response.getHeader(headerName))
                .collect(Collectors.joining(","));
        log.info(
                "TraceId: [{}], Response Header: [{}], Response Body: {}",
                traceId,
                header,
                Objects.equals(bodyString, "") ? "[]" : bodyString
        );
    }

    private void requestLog(HttpServletRequest request, byte[] bytes, String traceId) {
        String bodyString = new String(bytes, StandardCharsets.UTF_8);
        StringBuilder headerString = new StringBuilder();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while(headerEnumeration.hasMoreElements()) {
            String headerKey = headerEnumeration.nextElement();
            headerString.append(headerKey).append(": ").append(request.getHeader(headerKey)).append(",");
        }

        StringBuilder paramString = new StringBuilder();
        Enumeration<String> paramEnumeration = request.getParameterNames();
        while(paramEnumeration.hasMoreElements()) {
            String paramKey = paramEnumeration.nextElement();
            paramString.append(paramKey).append(": ").append(request.getParameter(paramKey)).append(",");
        }

        log.info(
                "TraceId: [{}], Request Method: [{}], Request Url: [{}], Request Header: [{}], Request Param: [{}], Request Body: {}",
                traceId,
                request.getMethod(),
                request.getRequestURL(),
                Objects.equals(headerString.toString(), "") ? "[]" : headerString.substring(0, headerString.length() - 1),
                Objects.equals(paramString.toString(), "") ? "[]" : paramString.substring(0, paramString.length() - 1),
                Objects.equals(bodyString, "") ? "[]" : bodyString
        );
    }
}
