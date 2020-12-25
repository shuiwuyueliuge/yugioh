package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.command.IncludeInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
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
        Iterator<List<MetaData<CardDTO>>> cardIterator = dataCenter.getCardData().obtainCards();
        while (cardIterator.hasNext()) {
            List<MetaData<CardDTO>> metaData = cardIterator.next();
            for (MetaData data : metaData) {
                // 对每个卡片发布领域事件
                data.createMetaData(cardListCreateCommand.getChannelId());
            }
        }
    }

    public void createIncludeInfo(IncludeInfoCreateCommand includeInfoCreateCommand) {
        // 获取数据中心
        DataCenterFactory dataCenter = dataCenterStrategy.findDataCenter(includeInfoCreateCommand.getDataCenter());
        // 获取收录信息
        List<MetaData<IncludeDTO>> metaDataList = dataCenter.getIncludeData().obtainIncludes(
                includeInfoCreateCommand.getCardPassword(),
                includeInfoCreateCommand.getResource()
        );

        if (metaDataList.size() == 0) {
            return;
        }

        metaDataList.forEach(metaData -> metaData.createMetaData(includeInfoCreateCommand.getChannelId()));
    }
}