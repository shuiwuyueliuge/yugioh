package cn.mayu.yugioh.minerva.application.card;

import cn.mayu.yugioh.common.facade.minerva.model.CardCreateCommand;
import cn.mayu.yugioh.minerva.domain.aggregate.card.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 卡片命令应用服务
 */
@Service
public class CardCommandService {

    @Autowired
    private CardRepository cardRepository;

    /**
     * 创建卡片
     *
     * @param cardCreateCommand 卡片数据
     */
    public void createCard(CardCreateCommand cardCreateCommand) {
        cardCreateCommand.getCardInfos().forEach(cardInfo -> {
            CardIdentity cardIdentity = new CardIdentity(cardInfo.getPassword());
            Name name = new Name(cardInfo.getName(), cardInfo.getNameJa(),
                    cardInfo.getNameEn(), cardInfo.getNameNw());
            Description description = new Description(cardInfo.getDesc(), cardInfo.getDescJa(),
                    cardInfo.getDescEn(), cardInfo.getDescNw());
            Monster monster = new Monster(cardInfo.getLevel(), cardInfo.getAttribute(), cardInfo.getRace(),
                    cardInfo.getAtk(), cardInfo.getDef(), cardInfo.getPend(), cardInfo.getLink(),
                    cardInfo.getLinkArrow());
            List<Include> includes = cardInfo.getInclude().stream().map(Include::init).collect(Collectors.toList());
            Card card = new Card(cardIdentity, name, monster, description, cardInfo.getImgUrl(),
                    cardInfo.getTypeSt(), includes, cardInfo.getAdjust(), cardInfo.getTypeVal());
            cardRepository.store(card);
            card.publishCardCreated();
        });
    }
}
