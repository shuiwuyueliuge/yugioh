package cn.mayu.yugioh.gateway.route.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @description: gateway 路由信息
 * @author: YgoPlayer
 * @time: 2021/7/14 6:47 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDetail {

    private String id;

    private List<RuleCommand> predicates;

    private List<RuleCommand> filters;

    private String uri;

    private int order;
}
