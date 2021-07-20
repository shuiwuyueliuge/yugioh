package cn.mayu.yugioh.gateway.route.domain.instance;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @description: 网关的实例信息
 * @author: YgoPlayer
 * @time: 2021/7/14 4:39 下午
 */
@ToString
@Getter
@AllArgsConstructor
public class GatewayInstance extends Entity {

    private final String name;

    private final String ip;

    private final int port;
}
