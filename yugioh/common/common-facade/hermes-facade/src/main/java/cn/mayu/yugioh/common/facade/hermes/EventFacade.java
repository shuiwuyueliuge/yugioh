package cn.mayu.yugioh.common.facade.hermes;

import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "hermes",
        contextId = "event",
        //fallback = EventFacadeFacadeFallback.class, // 对比fallbackFactory优先起作用
        fallbackFactory = EventFacadeFacadeFallbackFactory.class
)
public interface EventFacade {

    @PostMapping("/event/receive")
    void receiveEvent(@RequestBody EventReceiveCommand eventReceiveCommand);
}
