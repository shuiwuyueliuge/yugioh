package cn.mayu.yugioh.gateway.route.port.adapter.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGatewayRepository extends JpaRepository<GatewayDO, Long> {

    GatewayDO findByName(String name);
}
