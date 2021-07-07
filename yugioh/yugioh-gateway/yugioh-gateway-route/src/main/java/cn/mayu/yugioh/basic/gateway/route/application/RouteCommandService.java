package cn.mayu.yugioh.basic.gateway.route.application;

import cn.mayu.yugioh.basic.gateway.route.domain.*;
import cn.mayu.yugioh.common.basic.tool.BeanPropertiesCopier;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

/**
 * @description: 网关路由相关操作
 * @author: YgoPlayer
 * @time: 2021/6/23 6:58 下午
 */
@Service
public class RouteCommandService {

    private final RouteRepository routeRepository;

    public RouteCommandService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void addRoute(RouteCommand routeCommand) {
        synchronized (RouteCommandService.class) {
            if (!Objects.isNull(routeRepository.findByRouteId(routeCommand.getId()))) {
                throw new RuntimeException("route id:[" + routeCommand.getId() + "] exists");
            }
        }

        List<RuleCommand> predicates = routeCommand.getPredicates();
        List<RuleCommand> filters = routeCommand.getFilters();
        List<Rule> predicate = (!Objects.isNull(predicates) && !predicates.isEmpty()) ?
                BeanPropertiesCopier.copyProperties(predicates, Rule.class) : Lists.newArrayList();
        List<Rule> filter = (!Objects.isNull(filters) && !filters.isEmpty()) ?
                BeanPropertiesCopier.copyProperties(filters, Rule.class) : Lists.newArrayList();
        RouteInfo routeInfo = new RouteInfo(
                routeCommand.getId(),
                routeCommand.getUri(),
                routeCommand.getOrder(),
                predicate,
                filter
        );

        Route route = new Route(routeInfo, RouteStatus.CREATE);
        routeRepository.save(route);
        route.publishRouteEvent();
    }

    public void updateRoute(RouteCommand routeCommand) {
        Route route = routeRepository.findByRouteId(routeCommand.getId());
        route.update();
        routeRepository.save(route);
        route.publishRouteEvent();
    }

    public void delRoute(String routeId) {
        Route route = routeRepository.findByRouteId(routeId);
        route.del();
        routeRepository.save(route);
        route.publishRouteEvent();
    }
}
