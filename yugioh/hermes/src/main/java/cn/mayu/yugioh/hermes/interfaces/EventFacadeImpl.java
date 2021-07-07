package cn.mayu.yugioh.hermes.interfaces;

import cn.mayu.yugioh.common.facade.hermes.EventFacade;
import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import cn.mayu.yugioh.common.web.handler.RestWrapController;
import cn.mayu.yugioh.hermes.application.EventCommandService;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.TimeUnit;

@RestWrapController
public class EventFacadeImpl implements EventFacade {

    @Autowired
    private EventCommandService eventCommandService;

    @PostMapping("/event/receive")
    public void receiveEvent(@RequestBody EventReceiveCommand eventReceiveCommand) {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eventCommandService.receiveEvent(eventReceiveCommand);
    }
}