package cn.mayu.yugioh.basic.gateway.route.domain;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/24 11:30 上午
 */
@Getter
@AllArgsConstructor
public class RouteEvent implements DomainEvent {

    private final String serializationRoute;

    @Override
    public long occurredOn() {
        return System.currentTimeMillis();
    }
}
