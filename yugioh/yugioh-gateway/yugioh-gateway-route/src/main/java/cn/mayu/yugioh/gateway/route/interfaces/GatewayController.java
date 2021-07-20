package cn.mayu.yugioh.gateway.route.interfaces;

import cn.mayu.yugioh.gateway.route.application.*;
import cn.mayu.yugioh.common.web.handler.RestWrapController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @description: 路由相关的接口
 * 网关名称列表 -> 网关实例列表 + 网关路由配置信息
 * @author: YgoPlayer
 * @time: 2021/6/23 7:26 下午
 */
@RestWrapController
@AllArgsConstructor
public class GatewayController {
    
    private final GatewayCommandService gatewayCommandService;

    private final GatewayQueryService gatewayQueryService;

    @GetMapping("/gateway")
    public List<GatewayInfo> findGatewayList() {
        return gatewayQueryService.findGatewayList();
    }

    @GetMapping("/gateway/instance")
    public List<GatewayInstanceInfo> findGatewayInstanceList(@RequestParam("name") String name) {
        return gatewayQueryService.findGatewayInstanceList(name);
    }

    @GetMapping("/gateway/route")
    public List<RouteDetail> findGatewayRouteList(@RequestParam("name") String name) {
        return gatewayQueryService.findGatewayRouteList(name);
    }

    @PostMapping("/gateway")
    public void addGateway(@RequestBody GatewayCommand gatewayCommand) {
        gatewayCommandService.addGateway(gatewayCommand);
    }

    @PutMapping("/gateway")
    public void modifyGateway(@RequestBody GatewayCommand gatewayCommand) {
        gatewayCommandService.modifyGateway(gatewayCommand);
    }

    @PostMapping("/gateway/route")
    public void addRoute(@RequestBody RouteCommand routeCommand) {
        gatewayCommandService.addRoute(routeCommand);
    }

    @PutMapping("/gateway/route")
    public void modifyRoute(@RequestBody RouteCommand routeCommand) {
        gatewayCommandService.updateRoute(routeCommand);
    }

    @DeleteMapping("/gateway/route/{gatewayName}/{routeId}")
    public void removeRoute(@PathVariable String gatewayName, @PathVariable String routeId, @RequestParam String operateChannel) {
        gatewayCommandService.delRoute(gatewayName, routeId, operateChannel);
    }
}
