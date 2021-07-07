package cn.mayu.yugioh.basic.gateway.route.application;

import cn.mayu.yugioh.basic.gateway.route.domain.RouteEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import cn.mayu.yugioh.common.redis.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

/**
 * @description: 路由事件订阅
 * @author: YgoPlayer
 * @time: 2021/5/11 3:23 下午
 */
@Slf4j
@Component
public class RouteEventSubscribe implements DomainEventSubscribe<RouteEvent> {

    private final RedisOperator redisOperator;

    private static final byte[] ROUTE_KEY = "gateway:route".getBytes(StandardCharsets.UTF_8);

    public RouteEventSubscribe(RedisOperator redisOperator) {
        this.redisOperator = redisOperator;
    }

    @Override
    public void subscribe(RouteEvent mainEvent) {
        redisOperator.lPush(ROUTE_KEY, mainEvent.getSerializationRoute().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Class<RouteEvent> domainEventClass() {
        return RouteEvent.class;
    }
}