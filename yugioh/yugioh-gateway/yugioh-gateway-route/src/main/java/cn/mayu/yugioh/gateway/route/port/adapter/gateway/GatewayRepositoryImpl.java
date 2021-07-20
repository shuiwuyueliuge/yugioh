package cn.mayu.yugioh.gateway.route.port.adapter.gateway;

import cn.mayu.yugioh.gateway.route.domain.gateway.Gateway;
import cn.mayu.yugioh.gateway.route.domain.gateway.GatewayRepository;
import cn.mayu.yugioh.gateway.route.domain.gateway.OperateInfo;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: gateway crud
 * @author: YgoPlayer
 * @time: 2021/7/14 5:31 下午
 */
@Component
@AllArgsConstructor
public class GatewayRepositoryImpl implements GatewayRepository {

    private final JpaGatewayRepository jpaGatewayRepository;

    @Override
    public List<Gateway> findGatewayList() {
        List<GatewayDO> gateways = jpaGatewayRepository.findAll(Sort.by(Sort.Order.desc("create_time")));
        if (gateways.isEmpty()) {
            return Lists.newArrayList();
        }

        return gateways.stream().map(gateway -> new Gateway(
                gateway.getName(),
                gateway.getDescription(),
                new OperateInfo(
                        gateway.getCreateTime(),
                        gateway.getCreator(),
                        gateway.getModifyTime(),
                        gateway.getModifier()
                ))
        ).collect(Collectors.toList());
    }

    @Override
    public void save(Gateway gateway) {
        Long id = null;
        synchronized (GatewayRepositoryImpl.class) {
            GatewayDO saved = jpaGatewayRepository.findByName(gateway.getName());
            if (!Objects.isNull(saved)) {
                id = saved.getId();
            }
        }

        GatewayDO gatewayDO = new GatewayDO();
        gatewayDO.setId(id);
        gatewayDO.setDescription(gateway.getDescription());
        gatewayDO.setCreator(gateway.getOperateInfo().getCreator());
        gatewayDO.setCreateTime(gateway.getOperateInfo().getCreateTime());
        jpaGatewayRepository.save(gatewayDO);
    }
}
