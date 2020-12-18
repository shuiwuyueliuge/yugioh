package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;

/**
 * 数据中心命令服务
 */
@Service
public class DataCenterCommandService {

    @Autowired
    private DataCenterStrategy dataCenterStrategy;

    /**
     * 创建card目录
     * @param cardListCreateCommand
     */
    public void createCardList(CardInfoCreateCommand cardListCreateCommand) {
        DataCenterFactory dataCenter = dataCenterStrategy.findDataCenter(cardListCreateCommand.getDataCenter());
        Iterator<List<MetaData>> cardIterator = dataCenter.getCardData().obtainCards();
        while (cardIterator.hasNext()) {
            List<MetaData> metaData = cardIterator.next();
            for(MetaData data : metaData) {
                data.commitTo(cardListCreateCommand.getChannelId(), cardListCreateCommand.getDataCenter());
            }
        }
    }
}