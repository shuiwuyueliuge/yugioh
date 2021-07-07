package cn.mayu.yugioh.basic.gateway.route.interfaces;

import cn.mayu.yugioh.basic.gateway.route.application.RouteCommand;
import cn.mayu.yugioh.basic.gateway.route.application.RouteCommandService;
import cn.mayu.yugioh.common.web.handler.RestWrapController;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 路由相关的接口
 * @author: YgoPlayer
 * @time: 2021/6/23 7:26 下午
 */
@RestWrapController
public class RouteController {
    
    private final RouteCommandService routeCommandService;

    public RouteController(RouteCommandService routeCommandService) {
        this.routeCommandService = routeCommandService;
    }

    @PostMapping("/route")
    public void addRoute(@RequestBody RouteCommand routeCommand) {
        routeCommandService.addRoute(routeCommand);
    }

    @PutMapping("/route")
    public void modifyRoute(@RequestBody RouteCommand routeCommand) {
        routeCommandService.updateRoute(routeCommand);
    }

    @DeleteMapping("/route/{routeId}")
    public void removeRoute(@PathVariable String routeId) {
        routeCommandService.delRoute(routeId);
    }
}
