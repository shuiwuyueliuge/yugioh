package cn.mayu.yugioh.library.application.card;

import cn.mayu.yugioh.library.application.card.command.CardCreateCommand;
import cn.mayu.yugioh.library.domain.aggregate.card.*;
import org.springframework.stereotype.Service;

@Service
public class CardCommandService {

    public void createCard(CardCreateCommand cardCreateCommand) {
        CardIdentity cardIdentity = new CardIdentity(cardCreateCommand.getPassword());
        Name name = new Name(cardCreateCommand.getName(), cardCreateCommand.getNameJa(),
                cardCreateCommand.getNameEn(), cardCreateCommand.getNameNw());
        Description description = new Description(cardCreateCommand.getDesc(), cardCreateCommand.getDescJa(),
                cardCreateCommand.getDescEn(), cardCreateCommand.getDescNw());
        Monster monster = new Monster(cardCreateCommand.getLevel(), cardCreateCommand.getAttribute(), cardCreateCommand.getRace(),
                cardCreateCommand.getAtk(),cardCreateCommand.getDef(), cardCreateCommand.getPend(), cardCreateCommand.getLink(),
                cardCreateCommand.getLinkArrow());
        Card card = new Card(cardIdentity, name, monster, description, cardCreateCommand.getImgUrl(),
                cardCreateCommand.getTypeSt(), cardCreateCommand.getAdjust(), cardCreateCommand.getTypeVal());
        card.commitTo(cardCreateCommand.getChannel());
    }
}
