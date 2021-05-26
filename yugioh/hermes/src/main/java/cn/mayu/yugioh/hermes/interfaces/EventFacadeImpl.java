package cn.mayu.yugioh.hermes.interfaces;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.facade.hermes.EventFacade;
import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import cn.mayu.yugioh.hermes.application.EventCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventFacadeImpl implements EventFacade {

    @Autowired
    private EventCommandService eventCommandService;

    @Autowired
    private StreamBridge bridge;

    @PostMapping("/event/receive")
    public void receiveEvent(@RequestBody EventReceiveCommand eventReceiveCommand) {
        //eventCommandService.receiveEvent(eventReceiveCommand);

        RemoteDomainEvent domainEvent = new RemoteDomainEvent(
                System.currentTimeMillis(),
                "task-progress-in-0",
                "{\"taskId\":\"" + 456 + "\",\"taskInfo\":\"" + 123 + "\"}",
                "sadas"
        );

        bridge.send(domainEvent.getType(), domainEvent);
    }
}
