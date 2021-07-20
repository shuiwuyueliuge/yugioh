package cn.mayu.yugioh.gateway.route.domain.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/23 7:05 下午
 */
@Getter
@AllArgsConstructor
public enum RouteStatus {

    CREATE(1), UPDATE(0), DELETE(-1);

    private final int status;
}
