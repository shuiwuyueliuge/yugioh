package com.mayu.yugioh.common.web.reactive.handler;

import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import reactor.core.publisher.Mono;
import java.lang.annotation.Annotation;

/**
 * @description: 拦截标注了{@link {@link RestWrapController}} 的请求
 * @author: YgoPlayer
 * @time: 2021/5/31 3:59 下午
 */
public class WebFluxResultHandler extends MessageWriterResultHandler<RestResult> {

    private final int successCode;

    private final String successMsg;

    private static final int ORDER = 90;

    public WebFluxResultHandler(
            ReactiveAdapterRegistry reactiveAdapterRegistry,
            ServerCodecConfigurer serverCodecConfigurer,
            RequestedContentTypeResolver contentTypeResolver,
            RestCodeGenerator restCodeGenerator
    ) {
        super(serverCodecConfigurer.getWriters(), contentTypeResolver, reactiveAdapterRegistry);
        this.successCode = restCodeGenerator.restSuccessCode();
        this.successMsg = restCodeGenerator.restSuccessMsg();
    }

    @Override
    protected Class<? extends Annotation> getHandlerAnnotation() {
        return RestWrapController.class;
    }

    @Override
    protected int getOrderInt() {
        return ORDER;
    }

    @Override
    public Mono<RestResult> doHandleResult(Mono monoResult, Class<?> returnTypeClass) {
        return monoResult.defaultIfEmpty("").map(responseValue -> {
            if (Pageable.class == returnTypeClass) {
                return new PageRestResult(successCode, (Pageable<?>) responseValue, successMsg);
            }

            return new RestResult(successCode, responseValue, successMsg);
        });
    }

    @Override
    protected RestResult getReturnTypeSource() {
        return new RestResult();
    }
}
