package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;

/**
 * @description: {@link Resilience4JCircuitBreaker} 构造参数缓存
 * @author: YgoPlayer
 * @time: 2021/7/6 10:42 上午
 */
@Getter
@AllArgsConstructor
public class CircuitBreakerInfoCache {

    private final String id;

    private final io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfig;
}
