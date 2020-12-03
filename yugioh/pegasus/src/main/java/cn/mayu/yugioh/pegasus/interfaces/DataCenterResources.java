package cn.mayu.yugioh.pegasus.interfaces;

import cn.mayu.yugioh.pegasus.application.DataCenterCommandService;
import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataCenterResources {

    @Autowired
    private DataCenterCommandService dataCenterCommandService;

    @PostMapping("/card_data")
    public void createCardInfo(@RequestBody CardInfoCreateCommand cardInfoCreateCommand) {
        dataCenterCommandService.createCardList(cardInfoCreateCommand);
    }
}
