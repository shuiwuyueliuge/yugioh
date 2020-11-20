package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.command.MetaDataCreateCommand;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenter;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.exception.CardListCreateException;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfo;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfoRepository;
import cn.mayu.yugioh.pegasus.domain.aggregate.metadata.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * 数据中心命令服务
 */
@Service
public class DataCenterCommandService {

    @Autowired
    private DataCenterStrategy dataCenterStrategy;

    @Autowired
    private CardInfoRepository cardInfoRepository;

    /**
     * 创建元数据
     * @param metaDataCreateCommand 元数据创建命令对象
     */
    public void createMetaData(MetaDataCreateCommand metaDataCreateCommand) {
        // 通过数据中心获取数据
        DataCenter dataCenter = dataCenterStrategy.findDataCenter(metaDataCreateCommand.getDataCenter());
        List<MetaData> metaData = dataCenter.obtainMetaData(metaDataCreateCommand.getResources(), metaDataCreateCommand.getDataType());

        // 发布领域命令
        metaData.stream().forEach(MetaData::commitTo);
    }

    /**
     * 创建card目录
     * @param cardListCreateCommand
     */
    public void createCardList(CardInfoCreateCommand cardListCreateCommand) {
        DataCenter dataCenter = dataCenterStrategy.findDataCenter(cardListCreateCommand.getDataCenter());
        Optional<List<CardInfo>> cardListOptional = dataCenter.obtainCardList();
        if (!cardListOptional.isPresent()) {
            throw new CardListCreateException(cardListCreateCommand.getDataCenter() + " card list not found");
        }

        cardListOptional.get().stream().forEach(cardInfo -> {
            // TODO 事件溯源，观察者模式
            cardInfoRepository.store(cardInfo);
            cardInfo.commitTo();
        });
    }
}
