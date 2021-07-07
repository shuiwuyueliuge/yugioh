package cn.mayu.yugioh.basic.gateway.route.domain;

import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import cn.mayu.yugioh.common.basic.tool.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

/**
 * @description: 网关路由实体
 * @author: YgoPlayer
 * @time: 2021/6/23 6:52 下午
 */
@Getter
@AllArgsConstructor
public class Route extends Entity {

    private final RouteInfo routeInfo;

    private RouteStatus routeStatus;

    public void update() {
        this.routeStatus = RouteStatus.UPDATE;
    }

    public void del() {
        this.routeStatus = RouteStatus.DELETE;
    }

    @Override
    public String toString() {
        return JsonCreator.defaultInstance().writeValueAsString(this);
    }

    public void publishRouteEvent() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new RouteEvent(toString()));
    }
}
