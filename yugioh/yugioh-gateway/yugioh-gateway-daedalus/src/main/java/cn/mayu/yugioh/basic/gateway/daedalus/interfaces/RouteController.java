package cn.mayu.yugioh.basic.gateway.daedalus.interfaces;

import cn.mayu.yugioh.basic.gateway.daedalus.application.DynamicRouteCommandService;
import com.mayu.yugioh.common.web.reactive.handler.RestWrapController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

/**
 * @description: 路由相关接口
 * @author: YgoPlayer
 * @time: 2021/7/14 10:43 上午
 */
@AllArgsConstructor
@RestWrapController
public class RouteController {

    private final DynamicRouteCommandService routeCommandService;

    @PostMapping("/route")
    public Mono<Void> operate(DynamicRouteCommandService.RouteInfo routeInfo) {
        return routeCommandService.operate(routeInfo);
    }
}
