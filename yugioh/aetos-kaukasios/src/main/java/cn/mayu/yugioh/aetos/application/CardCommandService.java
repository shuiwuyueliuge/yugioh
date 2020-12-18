package cn.mayu.yugioh.aetos.application;

import cn.mayu.yugioh.aetos.domain.Card;
import cn.mayu.yugioh.aetos.domain.CardIdentity;
import cn.mayu.yugioh.aetos.domain.Monster;
import org.springframework.stereotype.Service;

@Service
public class CardCommandService {

    public void createCard(CardCreateCommand cardCreateCommand) {
        CardIdentity cardIdentity = new CardIdentity(cardCreateCommand.getPassword());
        Monster monster = new Monster(
                cardCreateCommand.getLink(),
                cardCreateCommand.getDef(),
                cardCreateCommand.getPend(),
                cardCreateCommand.getRace(),
                cardCreateCommand.getAttribute(),
                cardCreateCommand.getLevel(),
                cardCreateCommand.getAtk(),
                cardCreateCommand.getLinkArrow()
        );

        Card card = new Card(
                cardIdentity,
                monster,
                cardCreateCommand.getName(),
                cardCreateCommand.getDesc(),
                cardCreateCommand.getTypeVal(),
                cardCreateCommand.getTypeSt()
        );

        card.commitTo();
    }
}
