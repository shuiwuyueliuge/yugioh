package cn.mayu.yugioh.gateway.route.domain.route;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: route更新事件
 * @author: YgoPlayer
 * @time: 2021/6/24 11:30 上午
 */
@Getter
@AllArgsConstructor
public class RouteEvent implements DomainEvent {

    private final String name;

    private final int version;

    private final String operateChannel;

    private final RouteDefinition routeDefinition;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }

    @Getter
    @AllArgsConstructor
    public static class RouteDefinition {

        private final RouteInfo routeInfo;

        private final RouteStatus routeStatus;
    }
}
