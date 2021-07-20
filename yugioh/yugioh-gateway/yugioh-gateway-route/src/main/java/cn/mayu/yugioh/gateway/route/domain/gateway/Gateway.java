package cn.mayu.yugioh.gateway.route.domain.gateway;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @description: 网关聚合
 * @author: YgoPlayer
 * @time: 2021/7/14 4:36 下午
 */
@ToString
@Getter
@AllArgsConstructor
public class Gateway extends Entity {

    private final String name;

    private final String description;

    private final OperateInfo operateInfo;
}
