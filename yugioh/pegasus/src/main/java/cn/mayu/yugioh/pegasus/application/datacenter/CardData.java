package cn.mayu.yugioh.pegasus.application.datacenter;

import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaData;
import java.util.Iterator;
import java.util.List;

public interface CardData extends DataType {

    /**
     * 获取卡片信息
     */
    Iterator<List<MetaData>> obtainCards();

    CardDTO data2CardDTO(String cardData);
}
