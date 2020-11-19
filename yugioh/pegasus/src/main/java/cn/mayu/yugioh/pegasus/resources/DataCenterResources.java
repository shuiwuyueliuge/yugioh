package cn.mayu.yugioh.pegasus.resources;

import cn.mayu.yugioh.pegasus.application.DataCenterCommandService;
import cn.mayu.yugioh.pegasus.application.DataCenterQueryService;
import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.command.MetaDataCreateCommand;
import cn.mayu.yugioh.pegasus.application.dto.CardInfoDTO;
import cn.mayu.yugioh.pegasus.application.query.CardInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataCenterResources {

    @Autowired
    private DataCenterCommandService dataCenterCommandService;

    @Autowired
    private DataCenterQueryService dataCenterQueryService;

    @PostMapping("/meta_data")
    public void createMetaData(@RequestBody MetaDataCreateCommand metaDataCreateCommand) {
        dataCenterCommandService.createMetaData(metaDataCreateCommand);
    }

    @PostMapping("/card_info")
    public void createCardInfo(@RequestBody CardInfoCreateCommand cardInfoCreateCommand) {
        dataCenterCommandService.createCardList(cardInfoCreateCommand);
    }

    @GetMapping("/card_info")
    public List<CardInfoDTO> findCardInfo(CardInfoQuery cardListQuery) {
        return dataCenterQueryService.findCardInfo(cardListQuery);
    }
}
