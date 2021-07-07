package com.mayu.yugioh.common.web.reactive.trace;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/1 11:48 上午
 */
@Slf4j
public class WebFluxTraceFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .defaultIfEmpty(DefaultDataBufferFactory.sharedInstance.wrap(new byte[0]))
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                        return Mono.just(buffer);
                    });

                    String traceId = getTraceId(exchange.getRequest());
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
                            exchange.getRequest()) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                        }
                    };

                    ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
                        @Override
                        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                            return super.writeWith(DataBufferUtils.join(body)
                                    .defaultIfEmpty(DefaultDataBufferFactory.sharedInstance.wrap(new byte[0]))
                                    .map(resDataBuf -> {
                                byte[] content = new byte[resDataBuf.readableByteCount()];
                                resDataBuf.read(content);
                                responseLog(exchange.getResponse(), content, traceId);
                                exchange.getResponse().getHeaders().setContentLength(content.length);
                                return exchange.getResponse().bufferFactory().wrap(content);
                            }));
                        }
                    };

                    requestLog(exchange.getRequest(), bytes, traceId);
                    mutatedRequest.mutate().header(TraceManager.TRACE_ID, traceId);
                    return chain.filter(exchange.mutate().request(mutatedRequest).response(decoratedResponse).build())
                            .contextWrite(cm -> TraceManager.putTraceId(cm, traceId));
                });
    }

    private String getTraceId(ServerHttpRequest request) {
        List<String> traceIds = request.getHeaders().get(TraceManager.TRACE_ID);
        if (!Objects.isNull(traceIds) && !traceIds.isEmpty()) {
            return traceIds.get(0);
        }

        return TraceManager.genTraceId();
    }

    private void responseLog(ServerHttpResponse response, byte[] content, String traceId) {
        String bodyString = new String(content, StandardCharsets.UTF_8);
        log.info(
                "TraceId: [{}], Response Header: {}, Response Body: {}",
                traceId,
                response.getHeaders(),
                bodyString
        );
    }

    private void requestLog(ServerHttpRequest request, byte[] bytes, String traceId) {
        String bodyString = new String(bytes, StandardCharsets.UTF_8);
        log.info(
                "TraceId: [{}], Request Method: [{}], Request Uri: [{}], Request Header: {}, Request Param: {}, Request Body: {}",
                traceId,
                request.getMethodValue(),
                request.getURI(),
                request.getHeaders(),
                request.getQueryParams(),
                bodyString
        );
    }
}
