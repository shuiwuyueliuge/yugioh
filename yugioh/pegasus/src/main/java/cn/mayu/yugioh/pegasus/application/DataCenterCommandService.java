package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.application.command.MetaDataCreateCommand;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenter;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.application.exception.CardListCreateException;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfo;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfoRepository;
import cn.mayu.yugioh.pegasus.domain.aggregate.metadata.MetaData;
import cn.mayu.yugioh.pegasus.domain.aggregate.metadata.MetaDataIdentity;
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
        String metaDataStr = dataCenter.obtainMetaData(metaDataCreateCommand.getResources());

        // 创建元数据
        MetaDataIdentity metaDataIdentity = new MetaDataIdentity(metaDataCreateCommand.getDataCenter(),
                                                                 metaDataCreateCommand.getDataType());
        MetaData metaData = new MetaData(metaDataIdentity, metaDataStr);
        // 发布领域命令
        metaData.commitTo();
    }

    /**
     * 创建card目录
     * @param cardListCreateCommand
     */
    public void createCardList(CardInfoCreateCommand cardListCreateCommand) {
        DataCenter dataCenter = dataCenterStrategy.findDataCenter(cardListCreateCommand.getDataCenter());
        Optional<List<String>> cardListOptional = dataCenter.obtainCardList();
        if (!cardListOptional.isPresent()) {
            throw new CardListCreateException(cardListCreateCommand.getDataCenter() + " card list not found");
        }

        cardListOptional.get().stream().map(CardInfo::new).forEach(cardInfo -> {
            // TODO 事件溯源，观察者模式
            cardInfoRepository.store(cardInfo);
            cardInfo.commitTo();
        });
    }
}
