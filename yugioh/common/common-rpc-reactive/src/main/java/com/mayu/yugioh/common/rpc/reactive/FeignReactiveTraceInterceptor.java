package com.mayu.yugioh.common.rpc.reactive;

import com.google.common.collect.Lists;
import com.mayu.yugioh.common.web.reactive.trace.TraceManager;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/13 10:03 上午
 */
public class FeignReactiveTraceInterceptor implements ReactiveHttpRequestInterceptor {

    @Override
    public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
        return TraceManager.contextTraceId().map(traceId -> {
            reactiveHttpRequest.headers().put(TraceManager.TRACE_ID, Lists.newArrayList(traceId));
            return reactiveHttpRequest;
        });
    }
}
