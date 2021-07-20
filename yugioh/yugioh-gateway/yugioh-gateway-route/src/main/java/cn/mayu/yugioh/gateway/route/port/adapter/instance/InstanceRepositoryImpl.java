package cn.mayu.yugioh.gateway.route.port.adapter.instance;

import cn.mayu.yugioh.gateway.route.domain.instance.GatewayInstance;
import cn.mayu.yugioh.gateway.route.domain.instance.GatewayInstanceRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 注册中心获取gateway信息
 * @author: YgoPlayer
 * @time: 2021/7/14 4:43 下午
 */
@Component
@AllArgsConstructor
public class InstanceRepositoryImpl implements GatewayInstanceRepository {

    private final DiscoveryClient discoveryClient;

    @Override
    public List<GatewayInstance> findByGatewayName(String gatewayName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(gatewayName);
        if (Objects.isNull(instances) || instances.isEmpty()) {
            return Lists.newArrayList();
        }

        return instances.stream().map(instance -> new GatewayInstance(gatewayName, instance.getHost(), instance.getPort()))
                .collect(Collectors.toList());
    }
}
