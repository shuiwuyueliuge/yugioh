package cn.mayu.yugioh.minerva.port.adapter.persistence.card;

import cn.mayu.yugioh.common.basic.tool.BeanPropertiesCopier;
import cn.mayu.yugioh.minerva.domain.aggregate.card.Card;
import cn.mayu.yugioh.minerva.domain.aggregate.card.CardRepository;
import cn.mayu.yugioh.minerva.domain.aggregate.card.Include;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private JpaCardRepository jpaCardRepository;

    @Autowired
    private ElasticSearchCardRepository elasticSearchCardRepository;

    @Override
    public void store(Card card) {
        JpaCardDO saved = jpaCardRepository.findByPassword(card.getCardIdentity().getPassword());
        JpaCardDO cardDO = new JpaCardDO();
        if (saved != null) {
            cardDO.setId(saved.getId());
        }

        List<Include> includes = (Objects.isNull(card.getInclude()) || card.getInclude().isEmpty()) ?
                Lists.newArrayList() : card.getInclude();
        List<String> typeSt = (Objects.isNull(card.getTypeSt()) || card.getTypeSt().isEmpty()) ?
                Lists.newArrayList() : card.getTypeSt();
        List<String> linkArrow = (Objects.isNull(card.getMonster().getLinkArrow()) || card.getMonster().getLinkArrow().isEmpty()) ?
                Lists.newArrayList() : card.getMonster().getLinkArrow();

        cardDO.setPassword(Objects.isNull(card.getCardIdentity().getPassword()) ? "" : card.getCardIdentity().getPassword());
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
        cardDO.setLinkArrow(String.join(",", linkArrow));
        cardDO.setTypeSt(String.join(",", typeSt));
        cardDO.setAdjust(card.getAdjust());
        cardDO.setTypeVal(card.getTypeVal());
        cardDO.setInclude(includes.stream().map(Include::toString).collect(Collectors.joining(",")));
        jpaCardRepository.save(cardDO);

        ElasticSearchCardDO elasticSearchCard = new ElasticSearchCardDO();
        BeanPropertiesCopier.copyProperties(cardDO, elasticSearchCard);
        elasticSearchCardRepository.save(elasticSearchCard);
    }
}
