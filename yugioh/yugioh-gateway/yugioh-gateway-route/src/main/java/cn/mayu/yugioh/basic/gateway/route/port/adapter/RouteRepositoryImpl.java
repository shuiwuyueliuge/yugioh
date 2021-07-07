package cn.mayu.yugioh.basic.gateway.route.port.adapter;

import cn.mayu.yugioh.basic.gateway.route.domain.Route;
import cn.mayu.yugioh.basic.gateway.route.domain.RouteInfo;
import cn.mayu.yugioh.basic.gateway.route.domain.RouteRepository;
import cn.mayu.yugioh.basic.gateway.route.domain.Rule;
import cn.mayu.yugioh.common.basic.tool.BeanPropertiesCopier;
import cn.mayu.yugioh.common.basic.tool.JsonCreator;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class RouteRepositoryImpl implements RouteRepository {

	private final JpaRouteRepository routeRepository;

	public RouteRepositoryImpl(JpaRouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	@Override
	public void save(Route route) {
		RouteDO saved = routeRepository.findByRouteId(route.getRouteInfo().getId());
		if (!Objects.isNull(saved)) {
			BeanPropertiesCopier.copyProperties(route, saved);
			routeRepository.save(saved);
			return;
		}

		JsonCreator jsonCreator = JsonCreator.defaultInstance();
		RouteDO routeDO = BeanPropertiesCopier.newByProperties(route, RouteDO.class);
		routeDO.setRouteId(route.getRouteInfo().getId());
		routeDO.setModifyTime(LocalDateTime.now());
		routeDO.setSort(route.getRouteInfo().getOrder());
		routeDO.setFilters(jsonCreator.writeValueAsString(route.getRouteInfo().getFilters()));
		routeDO.setPredicates(jsonCreator.writeValueAsString(route.getRouteInfo().getPredicates()));
		routeRepository.save(routeDO);
	}

	@Override
	public Route findByRouteId(String id) {
		JsonParser jsonParser = JsonParser.defaultInstance();
		RouteDO routeDO = routeRepository.findByRouteId(id);
		if (Objects.isNull(routeDO)) {
			return null;
		}

		List<Rule> filters = jsonParser.readListValue(routeDO.getFilters(), Rule.class);
		List<Rule> predicates = jsonParser.readListValue(routeDO.getPredicates(), Rule.class);
		RouteInfo routeInfo = new RouteInfo(routeDO.getRouteId(), routeDO.getUri(), routeDO.getSort(), predicates, filters);
		return new Route(routeInfo, routeDO.getRouteStatus());
	}
}
