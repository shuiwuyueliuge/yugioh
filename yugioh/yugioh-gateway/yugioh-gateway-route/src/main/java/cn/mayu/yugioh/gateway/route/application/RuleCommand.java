package cn.mayu.yugioh.gateway.route.application;

import lombok.Data;

import java.util.Map;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/23 7:17 下午
 */
@Data
public class RuleCommand {

    private String name;

    private Map<String, String> args;
}
