package cn.mayu.yugioh.basic.gateway.route.port.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRouteRepository extends JpaRepository<RouteDO, Integer> {

    RouteDO findByRouteId(String id);
}
