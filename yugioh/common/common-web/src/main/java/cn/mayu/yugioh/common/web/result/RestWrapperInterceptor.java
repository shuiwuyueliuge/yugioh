//package cn.mayu.yugioh.common.web.result;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import java.util.Objects;
//
//import static com.mangoerp.operation.web.autoconfiguration.HttpRequestContext.getAttribute;
//import static com.mangoerp.operation.web.autoconfiguration.ResultWrapper.RESPONSE_BODY_ANN;
//
//@Aspect
//@ControllerAdvice
//@Slf4j
//public class RestWrapperInterceptor implements ResponseBodyAdvice<Object> {
//
//    private final RestCodeGenerator restCodeGenerator;
//
//    public RestWrapperInterceptor(RestCodeGenerator restCodeGenerator) {
//        this.restCodeGenerator = restCodeGenerator;
//    }
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return getAttribute(RESPONSE_BODY_ANN) != null;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
//                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
//                                  ServerHttpResponse response) {
//        RestResult result = handleBody(body);
//        log.info("{}", result);
//        if (Objects.requireNonNull(returnType.getMethod()).getReturnType().getTypeName().equals("java.lang.String")) {
//            return result.toString();
//        }
//
//        return result;
//    }
//
//    /**
//     * 对返回视图对象的封装
//     * @param body controller返回的结果，可能为异常对象
//     * @return 封装后的统一数据格式
//     */
//    private RestResult handleBody(Object body) {
//        if (body instanceof BaseException) {
//            BaseException exception = (BaseException) body;
//            if (exception.getSourceException() != null) { // 处理非业务异常
//                log.error("{}", exception.getSourceException());
//            } else { // 自定义业务异常
//                log.info("{}", exception.getRestMsg());
//            }
//
//            return new RestResult(exception.getCode(), exception.getRestMsg());
//        }
//
//        if (body instanceof Pageable) {
//            Pageable pageData = (Pageable) body;
//            return new PageRestResult(
//                    restCodeGenerator.successCode(),
//                    pageData.getData(),
//                    pageData.getTotal(),
//                    pageData.getPageNum(),
//                    pageData.getPageSize()
//            );
//        }
//
//        return new RestResult(restCodeGenerator.successCode(), body);
//    }
//}