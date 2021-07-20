package cn.mayu.yugioh.gateway.route.port.adapter.route;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaRouteRepository extends JpaRepository<RouteDO, Integer> {

    RouteDO findByGatewayNameAndRouteId(String name, String id);

    List<RouteDO> findByGatewayName(String name);
}
