package cn.mayu.yugioh.gateway.route.application;

import lombok.Data;

/**
 * @description: gateway 命令对象
 * @author: YgoPlayer
 * @time: 2021/7/14 6:59 下午
 */
@Data
public class GatewayCommand {

    private String name;

    private String description;

    private String creator;

    private String modifier;
}
