package cn.mayu.yugioh.pegasus.infrastructure.convert;

import cn.mayu.yugioh.common.basic.convert.ModelConvertFactory;
import cn.mayu.yugioh.pegasus.application.dto.CardInfoDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardinfo.CardInfo;
import org.springframework.stereotype.Component;

@Component
public class CardInfoToCardInfoDTO implements ModelConvertFactory<CardInfo, CardInfoDTO> {

    @Override
    public CardInfoDTO convert(CardInfo source) {
        return new CardInfoDTO(source.getCardImg(), source.getCardName(), source.getCardUrl());
    }
}
