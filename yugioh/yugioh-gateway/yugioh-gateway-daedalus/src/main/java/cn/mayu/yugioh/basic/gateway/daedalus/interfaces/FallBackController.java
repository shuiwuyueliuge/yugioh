package cn.mayu.yugioh.basic.gateway.daedalus.interfaces;

import com.mayu.yugioh.common.web.reactive.expection.BaseException;
import com.mayu.yugioh.common.web.reactive.handler.RestWrapController;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * @description: 熔断器fallback
 * @author: YgoPlayer
 * @time: 2021/7/14 10:36 上午
 */
@RestWrapController
public class FallBackController {

    @RequestMapping("/fallback")
    public Mono<Void> fallback() {
        throw new FallbackError(500, "please retry");
    }

    public static class FallbackError extends BaseException {

        public FallbackError(int code, String restMsg) {
            super(code, restMsg);
        }
    }
}
