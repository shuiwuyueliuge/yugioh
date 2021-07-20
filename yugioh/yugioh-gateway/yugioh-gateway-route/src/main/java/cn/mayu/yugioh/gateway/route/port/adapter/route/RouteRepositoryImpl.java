package cn.mayu.yugioh.gateway.route.port.adapter.route;

import cn.mayu.yugioh.gateway.route.domain.route.Route;
import cn.mayu.yugioh.gateway.route.domain.route.RouteInfo;
import cn.mayu.yugioh.gateway.route.domain.route.RouteRepository;
import cn.mayu.yugioh.gateway.route.domain.route.Rule;
import cn.mayu.yugioh.common.basic.tool.BeanPropertiesCopier;
import cn.mayu.yugioh.common.basic.tool.JsonCreator;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {

	private final JpaRouteRepository routeRepository;

	@Override
	public void save(Route route) {
		RouteDO saved = routeRepository.findByGatewayNameAndRouteId(route.getName(), route.getRouteInfo().getId());
		if (!Objects.isNull(saved)) {
			BeanPropertiesCopier.copyProperties(route, saved);
			routeRepository.save(saved);
			return;
		}

		JsonCreator jsonCreator = JsonCreator.defaultInstance();
		RouteDO routeDO = BeanPropertiesCopier.newByProperties(route, RouteDO.class);
		routeDO.setRouteId(route.getRouteInfo().getId());
		routeDO.setModifyTime(LocalDateTime.now());
		routeDO.setUri(route.getRouteInfo().getUri());
		routeDO.setSort(route.getRouteInfo().getOrder());
		routeDO.setVersion(route.getVersion());
		routeDO.setFilters(jsonCreator.writeValueAsString(route.getRouteInfo().getFilters()));
		routeDO.setPredicates(jsonCreator.writeValueAsString(route.getRouteInfo().getPredicates()));
		routeRepository.save(routeDO);
	}

	@Override
	public Route findByGatewayNameAndRouteId(String name, String id) {
		JsonParser jsonParser = JsonParser.defaultInstance();
		RouteDO routeDO = routeRepository.findByGatewayNameAndRouteId(name, id);
		if (Objects.isNull(routeDO)) {
			return null;
		}

		return getRoute(name, jsonParser, routeDO);
	}

	@Override
	public List<Route> findByGatewayName(String name) {
		JsonParser jsonParser = JsonParser.defaultInstance();
		List<RouteDO> routeDOS = routeRepository.findByGatewayName(name);
		if (routeDOS.isEmpty()) {
			return null;
		}

		return routeDOS.stream().map(routeDO -> getRoute(name, jsonParser, routeDO)).collect(Collectors.toList());
	}

	private Route getRoute(String name, JsonParser jsonParser, RouteDO routeDO) {
		List<Rule> filters = jsonParser.readListValue(routeDO.getFilters(), Rule.class);
		List<Rule> predicates = jsonParser.readListValue(routeDO.getPredicates(), Rule.class);
		RouteInfo routeInfo = new RouteInfo(routeDO.getRouteId(), routeDO.getUri(), routeDO.getSort(), predicates, filters);
		return new Route(name, routeInfo, routeDO.getRouteStatus(), routeDO.getVersion());
	}
}
