package cn.mayu.yugioh.gateway.route.application;

import lombok.Data;

/**
 * @description: gateway实例信息
 * @author: YgoPlayer
 * @time: 2021/7/14 5:51 下午
 */
@Data
public class GatewayInstanceInfo {

    private final String ip;

    private final int port;
}
