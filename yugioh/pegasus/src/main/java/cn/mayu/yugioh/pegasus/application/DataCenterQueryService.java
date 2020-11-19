package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.common.basic.exception.convert.ModelConvertFactory;
import cn.mayu.yugioh.pegasus.application.dto.CardInfoDTO;
import cn.mayu.yugioh.pegasus.application.query.CardInfoQuery;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfo;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 数据中心查询服务
 */
@Service
public class DataCenterQueryService {

    @Autowired
    private CardInfoRepository cardInfoRepository;

    @Autowired
    private ModelConvertFactory<CardInfo, CardInfoDTO> cardInfoCardInfoDTOConvertFactory;

    public List<CardInfoDTO> findCardInfo(CardInfoQuery cardListQuery) {
        List<CardInfo> cardInfos = cardInfoRepository.findByDataCenter(cardListQuery.getDataCenter(),
                                                                        cardListQuery.getPage(),
                                                                        cardListQuery.getPageSize());
        return cardInfoCardInfoDTOConvertFactory.convert(cardInfos);
    }
}
