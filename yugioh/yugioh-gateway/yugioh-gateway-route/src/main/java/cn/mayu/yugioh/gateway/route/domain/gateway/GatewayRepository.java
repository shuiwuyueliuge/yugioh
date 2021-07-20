package cn.mayu.yugioh.gateway.route.domain.gateway;

import java.util.List;

public interface GatewayRepository {

    List<Gateway> findGatewayList();

    void save(Gateway gateway);
}
