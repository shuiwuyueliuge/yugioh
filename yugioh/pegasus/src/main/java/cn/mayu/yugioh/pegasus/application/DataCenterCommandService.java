package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenter;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        DataCenter dataCenter = dataCenterStrategy.findDataCenter(cardListCreateCommand.getDataCenter());
        dataCenter.obtainCards();
        // 通知进度
        // mq
    }
}
