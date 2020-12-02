package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.domain.aggregate.Card;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenter;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenterStrategy;
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
     *
     * @param cardListCreateCommand
     */
    public void createCardList(CardInfoCreateCommand cardListCreateCommand) {
        DataCenter dataCenter = dataCenterStrategy.findDataCenter(cardListCreateCommand.getDataCenter());
        Iterator<List<Card>> cardIterator = dataCenter.obtainCards();
        while (cardIterator.hasNext()) {
            List<Card> cards = cardIterator.next();
            cards.forEach(Card::commitTo);
        }
    }
}
