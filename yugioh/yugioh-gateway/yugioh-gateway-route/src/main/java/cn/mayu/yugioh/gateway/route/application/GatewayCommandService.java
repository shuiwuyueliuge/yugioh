package cn.mayu.yugioh.gateway.route.application;

import cn.mayu.yugioh.common.basic.tool.BeanPropertiesCopier;
import cn.mayu.yugioh.gateway.route.domain.gateway.Gateway;
import cn.mayu.yugioh.gateway.route.domain.gateway.GatewayRepository;
import cn.mayu.yugioh.gateway.route.domain.gateway.OperateInfo;
import cn.mayu.yugioh.gateway.route.domain.route.*;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @description: 网关路由相关操作
 * @author: YgoPlayer
 * @time: 2021/6/23 6:58 下午
 */
@Service
@AllArgsConstructor
public class GatewayCommandService {

    private final GatewayRepository gatewayRepository;

    private final RouteRepository routeRepository;

    public void addRoute(RouteCommand routeCommand) {
        synchronized (GatewayCommandService.class) {
            if (!Objects.isNull(routeRepository.findByGatewayNameAndRouteId(routeCommand.getGatewayName(), routeCommand.getId()))) {
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

        Route route = new Route(routeCommand.getGatewayName(), routeInfo, RouteStatus.CREATE, 0);
        routeRepository.save(route);
        route.publishRouteEvent(routeCommand.getOperateChannel());
    }

    public void updateRoute(RouteCommand routeCommand) {
        Route route = routeRepository.findByGatewayNameAndRouteId(routeCommand.getGatewayName(), routeCommand.getId());
        if (Objects.isNull(route)) {
            throw new RuntimeException("route id:[" + routeCommand.getId() + "] not found");
        }

        route.update(routeCommand.getVersion());
        routeRepository.save(route);
        route.publishRouteEvent(routeCommand.getOperateChannel());
    }

    public void delRoute(String gatewayName, String routeId, String operateChannel) {
        Route route = routeRepository.findByGatewayNameAndRouteId(gatewayName, routeId);
        route.del();
        routeRepository.save(route);
        route.publishRouteEvent(operateChannel);
    }

    public void addGateway(GatewayCommand gatewayCommand) {
        OperateInfo operateInfo = new OperateInfo(LocalDateTime.now(), gatewayCommand.getCreator(), null, "");
        Gateway gateway = new Gateway(gatewayCommand.getName(), gatewayCommand.getDescription(), operateInfo);
        gatewayRepository.save(gateway);
    }

    public void modifyGateway(GatewayCommand gatewayCommand) {
        OperateInfo operateInfo = new OperateInfo(null, "", LocalDateTime.now(), gatewayCommand.getModifier());
        Gateway gateway = new Gateway(gatewayCommand.getName(), gatewayCommand.getDescription(), operateInfo);
        gatewayRepository.save(gateway);
    }
}
