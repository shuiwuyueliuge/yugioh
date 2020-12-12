package cn.mayu.yugioh.library.infrastructure.repository.card;

import cn.mayu.yugioh.library.domain.aggregate.card.Card;
import cn.mayu.yugioh.library.domain.aggregate.card.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private JpaCardRepository jpaCardRepository;

    @Override
    public synchronized void store(Card card) {
        CardDO saved = jpaCardRepository.findByPassword(card.getCardIdentity().getPassword());
        CardDO cardDO = new CardDO();
        if (saved != null) {
            cardDO.setId(saved.getId());
        }

        cardDO.setPassword(card.getCardIdentity().getPassword());
        cardDO.setName(card.getName().getName());
        cardDO.setNameEn(card.getName().getNameEn());
        cardDO.setNameJa(card.getName().getNameJa());
        cardDO.setNameNw(card.getName().getNameNw());
        cardDO.setImgUrl(card.getImgUrl());
        cardDO.setLevel(card.getMonster().getLevel());
        cardDO.setAttribute(card.getMonster().getAttribute());
        cardDO.setRace(card.getMonster().getRace());
        cardDO.setAtk(card.getMonster().getAtk());
        cardDO.setDef(card.getMonster().getDef());
        cardDO.setPend(card.getMonster().getPend());
        cardDO.setDescription(card.getDescription().getDesc());
        cardDO.setDescJa(card.getDescription().getDescJa());
        cardDO.setDescEn(card.getDescription().getDescEn());
        cardDO.setDescNw(card.getDescription().getDescNw());
        cardDO.setLink(card.getMonster().getLink());
        cardDO.setLinkArrow(card.getMonster().getLinkArrow().stream().collect(Collectors.joining(",")));
        cardDO.setTypeSt(card.getTypeSt().stream().collect(Collectors.joining(",")));
        cardDO.setAdjust(card.getAdjust());
        cardDO.setTypeVal(card.getTypeVal());
        jpaCardRepository.save(cardDO);
    }
}
