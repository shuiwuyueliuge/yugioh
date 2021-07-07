package cn.mayu.yugioh.basic.gateway.route.domain;

public interface RouteRepository {

    void save(Route route);

    Route findByRouteId(String id);
}
