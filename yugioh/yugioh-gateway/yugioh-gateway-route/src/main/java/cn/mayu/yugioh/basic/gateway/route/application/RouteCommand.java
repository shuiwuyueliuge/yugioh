package cn.mayu.yugioh.basic.gateway.route.application;

import lombok.Data;
import java.util.List;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/23 7:04 下午
 */
@Data
public class RouteCommand {

    private String id;

    private List<RuleCommand> predicates;

    private List<RuleCommand> filters;

    private String uri;

    private int order;
}
