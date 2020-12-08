package cn.mayu.yugioh.postman.interfaces.facade;

import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.postman.application.EventCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventFacadeImpl {

    @Autowired
    private EventCommandService eventCommandService;

    @PostMapping("/event/receive")
    public void receiveEvent(@RequestBody EventReceiveCommand eventReceiveCommand) {
        eventCommandService.receiveEvent(eventReceiveCommand);
    }
}
