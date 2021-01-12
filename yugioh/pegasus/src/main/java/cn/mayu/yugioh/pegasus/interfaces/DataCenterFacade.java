package cn.mayu.yugioh.pegasus.interfaces;

import cn.mayu.yugioh.common.web.result.ResultWrapper;
import cn.mayu.yugioh.pegasus.application.DataCenterCommandService;
import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.command.IncludeInfoCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ResultWrapper
@RestController
public class DataCenterFacade {

    @Autowired
    private DataCenterCommandService dataCenterCommandService;

    @PostMapping("/card_data")
    public String createCardInfo(@RequestBody CardInfoCreateCommand cardInfoCreateCommand) {
        new Thread(() -> dataCenterCommandService.createCardList(cardInfoCreateCommand)).start();
        return "1";
    }

    @PostMapping("/include_data")
    public String createIncludeInfo(@RequestBody IncludeInfoCreateCommand includeInfoCreateCommand) {
        new Thread(() -> dataCenterCommandService.createIncludeInfo(includeInfoCreateCommand)).start();
        return "1";
    }
}
