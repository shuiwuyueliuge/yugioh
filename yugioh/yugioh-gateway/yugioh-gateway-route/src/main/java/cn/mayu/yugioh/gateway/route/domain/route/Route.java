package cn.mayu.yugioh.gateway.route.domain.route;

import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 网关路由实体
 * @author: YgoPlayer
 * @time: 2021/6/23 6:52 下午
 */
@Getter
@AllArgsConstructor
public class Route extends Entity {

    private final String name;

    private final RouteInfo routeInfo;

    private RouteStatus routeStatus;

    private int version;

    public void update(int currentVersion) {
        this.routeStatus = RouteStatus.UPDATE;
        synchronized (Route.class) {
            if (currentVersion < version) {
                throw new RuntimeException("currentVersion(" + currentVersion + ") < version(" + version + ")");
            }
        }

        version++;
    }

    public void del() {
        this.routeStatus = RouteStatus.DELETE;
    }

    public void publishRouteEvent(String operateChannel) {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new RouteEvent(name, version, operateChannel, new RouteEvent.RouteDefinition(routeInfo, routeStatus)));
    }
}
