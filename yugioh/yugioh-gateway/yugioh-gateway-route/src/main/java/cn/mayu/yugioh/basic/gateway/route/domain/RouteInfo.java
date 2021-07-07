package cn.mayu.yugioh.basic.gateway.route.domain;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/24 1:37 下午
 */
@Data
@AllArgsConstructor
public class RouteInfo extends ValueObject {

    private final String id;

    private final String uri;

    private final int order;

    private final List<Rule> predicates;

    private final List<Rule> filters;
}
