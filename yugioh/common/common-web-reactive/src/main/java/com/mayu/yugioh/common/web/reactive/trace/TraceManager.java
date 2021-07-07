package com.mayu.yugioh.common.web.reactive.trace;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.UUID;

/**
 * @description: trace id 管理
 * @author: YgoPlayer
 * @time: 2021/6/10 9:19 上午
 */
public class TraceManager {

    public static final String TRACE_ID = "trace-id";

    public static String genTraceId() {
        return String.join("", UUID.randomUUID().toString().split("-"));
    }

    public static Context putTraceId(Context context, String traceId) {
        return context.put(TRACE_ID, traceId);
    }

    public static Mono<String> contextTraceId() {
        return Mono.deferContextual(cmf -> {
            if(cmf.hasKey(TRACE_ID)) {
                return Mono.just(cmf.get(TRACE_ID));
            }

            return Mono.empty();
        });
    }
}
