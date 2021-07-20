package cn.mayu.yugioh.gateway.route.domain.instance;

import java.util.List;

public interface GatewayInstanceRepository {

    List<GatewayInstance> findByGatewayName(String gatewayName);
}
