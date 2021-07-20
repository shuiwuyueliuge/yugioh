package cn.mayu.yugioh.gateway.route.application;

import cn.mayu.yugioh.common.basic.tool.BeanPropertiesCopier;
import cn.mayu.yugioh.gateway.route.domain.gateway.GatewayRepository;
import cn.mayu.yugioh.gateway.route.domain.instance.GatewayInstanceRepository;
import cn.mayu.yugioh.gateway.route.domain.route.RouteRepository;
import cn.mayu.yugioh.gateway.route.domain.route.Rule;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: gateway查询服务
 * @author: YgoPlayer
 * @time: 2021/7/14 4:55 下午
 */
@Service
@AllArgsConstructor
public class GatewayQueryService {

    private final GatewayRepository gatewayRepository;

    private final GatewayInstanceRepository instanceRepository;

    private final RouteRepository routeRepository;

    public List<GatewayInfo> findGatewayList() {
        return gatewayRepository.findGatewayList().stream().map(data ->
                new GatewayInfo(
                        data.getName(),
                        data.getDescription(),
                        data.getOperateInfo().getCreateTime(),
                        data.getOperateInfo().getCreator(),
                        data.getOperateInfo().getModifyTime(),
                        data.getOperateInfo().getModifier()
                )
        ).collect(Collectors.toList());
    }

    public List<GatewayInstanceInfo> findGatewayInstanceList(String name) {
        return instanceRepository.findByGatewayName(name).stream()
                .map(data -> new GatewayInstanceInfo(data.getIp(), data.getPort()))
                .collect(Collectors.toList());
    }

    public List<RouteDetail> findGatewayRouteList(String name) {
        return routeRepository.findByGatewayName(name).stream().map(data -> {
            List<Rule> predicates = data.getRouteInfo().getPredicates();
            List<Rule> filters = data.getRouteInfo().getFilters();
            List<RuleCommand> predicate = (!Objects.isNull(predicates) && !predicates.isEmpty()) ?
                    BeanPropertiesCopier.copyProperties(predicates, RuleCommand.class) : Lists.newArrayList();
            List<RuleCommand> filter = (!Objects.isNull(filters) && !filters.isEmpty()) ?
                    BeanPropertiesCopier.copyProperties(filters, RuleCommand.class) : Lists.newArrayList();
            return new RouteDetail(
                    data.getRouteInfo().getId(),
                    predicate,
                    filter,
                    data.getRouteInfo().getUri(),
                    data.getRouteInfo().getOrder()
            );
        }).collect(Collectors.toList());
    }
}
