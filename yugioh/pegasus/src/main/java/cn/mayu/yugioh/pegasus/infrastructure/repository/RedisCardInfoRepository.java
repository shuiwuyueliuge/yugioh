package cn.mayu.yugioh.pegasus.infrastructure.repository;

import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfo;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfoRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RedisCardInfoRepository implements CardInfoRepository {

    @Override
    public void store(CardInfo cardList) {

    }

    @Override
    public List<CardInfo> findByDataCenter(String dataCenter, Integer page, Integer pageSize) {
        return null;
    }
}
