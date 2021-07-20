package com.mayu.yugioh.common.web.reactive.handler;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.util.Objects;

/**
 * @description: 拦截过滤器响应信息,把结果转换为RestResult
 * @author: YgoPlayer
 * @time: 2021/6/1 11:48 上午
 */
@Slf4j
public class FilterBodyHandler implements WebFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                return super.writeWith(DataBufferUtils.join(body)
                        .defaultIfEmpty(DefaultDataBufferFactory.sharedInstance.wrap(new byte[0]))
                        .map(resDataBuf -> {
                            byte[] content = new byte[resDataBuf.readableByteCount()];
                            resDataBuf.read(content);
                            Object result = exchange.getAttributes().get("result");
                            if (!Objects.isNull(result) && result instanceof RestResult) {
                                content = RestResult.toBytes((RestResult) result);
                            }

                            exchange.getResponse().getHeaders().setContentLength(content.length);
                            return exchange.getResponse().bufferFactory().wrap(content);
                        }));
            }
        };

        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    /**
     * 从低到高的顺序，即最低值具有高优先级
     *
     * @return 优先级
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 9;
    }
}
