package cn.mayu.yugioh.common.web.handler;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @description: 拦截标注了{@link {@link RestWrapController}} 的请求
 * @author: YgoPlayer
 * @time: 2021/5/31 3:59 下午
 */
@ControllerAdvice
public class WebResultHandler implements ResponseBodyAdvice<Object> {

    private final int successCode;

    private final String successMsg;

    public WebResultHandler(RestCodeGenerator restCodeGenerator) {
        this.successCode = restCodeGenerator.restSuccessCode();
        this.successMsg = restCodeGenerator.restSuccessMsg();
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Class<?> containingClass = methodParameter.getContainingClass();
        return AnnotatedElementUtils.hasAnnotation(containingClass, RestWrapController.class) ||
                methodParameter.hasMethodAnnotation(RestWrapController.class);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter methodParameter,
            MediaType mediaType,
            Class<? extends HttpMessageConverter<?>> aClass,
            ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse
    ) {
        return body instanceof Pageable ?
                new PageRestResult(successCode, (Pageable<?>) body, successMsg) :
                new RestResult(successCode, body, successMsg);
    }
}

