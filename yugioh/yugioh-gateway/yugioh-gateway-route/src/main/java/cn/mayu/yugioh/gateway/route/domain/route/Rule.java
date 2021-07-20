package cn.mayu.yugioh.gateway.route.domain.route;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * @description: 路由规则，断言和过滤器
 * @author: YgoPlayer
 * @time: 2021/6/23 7:15 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rule extends ValueObject {

    private String name;

    private Map<String, String> args;
}
