package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.application.datacenter.EventEnum;
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
     */
    public void createCardList(CardInfoCreateCommand cardListCreateCommand) {
        // 获取数据中心
        DataCenterFactory dataCenter = dataCenterStrategy.findDataCenter(cardListCreateCommand.getDataCenter());
        // 获取卡片信息
        Iterator<List<MetaData>> cardIterator = dataCenter.getCardData().obtainCards();
        while (cardIterator.hasNext()) {
            List<MetaData> metaData = cardIterator.next();
            for (MetaData data : metaData) {
                // 对每个卡片发布领域事件
                Object changeObj = null;
                if (EventEnum.CARD.getType().equals(data.getMetaDataIdentity().getType())) {
                    changeObj = dataCenter.getCardData().data2CardDTO(data.getData());
                }

                if (EventEnum.INCLUDE.getType().equals(data.getMetaDataIdentity().getType())) {
                    changeObj = dataCenter.getIncludeData().data2IncludeDTO(data.getData());
                }

                data.commitTo(cardListCreateCommand.getChannelId(), changeObj);
            }
        }
    }
}