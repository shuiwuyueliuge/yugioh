package cn.mayu.yugioh.gateway.route.domain.route;

import java.util.List;

public interface RouteRepository {

    void save(Route route);

    Route findByGatewayNameAndRouteId(String name, String id);

    List<Route> findByGatewayName(String name);
}
