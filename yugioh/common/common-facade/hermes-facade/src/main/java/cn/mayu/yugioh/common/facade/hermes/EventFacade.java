package cn.mayu.yugioh.common.facade.hermes;

import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hermes", contextId = "event"
        //, fallbackFactory = CardSourceFacadeFallbackFactory.class
)
public interface EventFacade {

    @PostMapping("/event/receive")
    void receiveEvent(@RequestBody EventReceiveCommand eventReceiveCommand);
}