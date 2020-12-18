package cn.mayu.yugioh.aetos.infrastructure.repository.card;

import cn.mayu.yugioh.aetos.domain.Card;
import cn.mayu.yugioh.aetos.domain.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private ElasticSearchCardRepository cardRepository;

    @Override
    public synchronized void store(Card card) {
        cardRepository.save(new CardDO(
                card.getCardIdentity().getPassword(),
                card.getName(),
                card.getDesc(),
                card.getTypeVal(),
                card.getMonster().getLink(),
                card.getMonster().getDef(),
                card.getMonster().getPend(),
                card.getMonster().getRace(),
                card.getMonster().getAttribute(),
                card.getMonster().getLevel(),
                card.getMonster().getAtk(),
                card.getTypeSt(),
                card.getMonster().getLinkArrow()
        ));
    }
}
