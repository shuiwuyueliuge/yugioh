package cn.mayu.yugioh.common.web.result;

import org.springframework.core.MethodParameter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class ResponseWrapper extends ResponseBodyResultHandler {

    private static MethodParameter param;

    static {
        try {
            //get new params
            param = new MethodParameter(ResponseWrapper.class
                    .getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public ResponseWrapper(ServerCodecConfigurer serverCodecConfigurer, RequestedContentTypeResolver resolver) {
        super(serverCodecConfigurer.getWriters(), resolver);
    }

    @Override
    public boolean supports(HandlerResult result) {
        boolean isMono = result.getReturnType().resolve() == Mono.class;
        boolean isAlreadyResponse = result.getReturnType().resolveGeneric(0) == RestResult.class;
        return isMono && !isAlreadyResponse;
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Mono<RestResult> body = ((Mono<Object>) result.getReturnValue()).map(RestResult::new)
                .defaultIfEmpty(null);
        return writeBody(body, param, exchange);
    }

    private static Mono<RestResult> methodForParams() {
        return null;
    }
}