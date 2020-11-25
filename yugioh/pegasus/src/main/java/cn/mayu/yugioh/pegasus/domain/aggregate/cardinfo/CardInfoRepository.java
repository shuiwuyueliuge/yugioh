package cn.mayu.yugioh.pegasus.domain.aggregate.cardinfo;

import java.util.List;

public interface CardInfoRepository {
    
    void store(CardInfo cardList);

    List<CardInfo> findByDataCenter(String dataCenter, Integer page, Integer pageSize);
}
