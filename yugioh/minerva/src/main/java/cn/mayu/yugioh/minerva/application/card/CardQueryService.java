//package cn.mayu.yugioh.minerva.application.card;
//
//import cn.mayu.yugioh.common.facade.minerva.model.CardInfo;
//import cn.mayu.yugioh.common.web.result.Pageable;
//import cn.mayu.yugioh.minerva.application.RestCode;
//import cn.mayu.yugioh.minerva.application.exception.DataCenterNotFoundException;
//import cn.mayu.yugioh.minerva.domain.aggregate.card.Card;
//import cn.mayu.yugioh.minerva.domain.aggregate.card.CardService;
//import cn.mayu.yugioh.minerva.domain.aggregate.card.Include;
//import cn.mayu.yugioh.minerva.domain.aggregate.datacenter.DataCenterHolder;
//import cn.mayu.yugioh.minerva.domain.aggregate.datacenter.DataCenterService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * 卡片查询服务
// */
//@Service
//public class CardQueryService {
//
//    @Autowired
//    private DataCenterHolder centerHolder;
//
//    /**
//     * 查询卡片列表
//     *
//     * @param dataCenterId 数据来源id
//     * @param searchCondition 查询条件，卡包查询，禁卡表查询
//     * @param page       当前页数
//     * @param pageSize   每页大小
//     * @return 卡片列表
//     */
//    public Pageable<CardInfo> searchCardList(String dataCenterId, String searchCondition, int page, int pageSize) {
//        DataCenterService dataCenterService = centerHolder.find(dataCenterId);
//        if (Objects.isNull(dataCenterService)) {
//            throw new DataCenterNotFoundException(RestCode.CARD.code, "dataCenter not development completed");
//        }
//
//        CardService cardService = dataCenterService.initCardService();
//        Collection<Card> cards = cardService.searchCardList(searchCondition, page, pageSize);
//        if (cards.isEmpty()) {
//            throw new DataCenterNotFoundException(RestCode.CARD.code, "no card found");
//        }
//
//        Long count = cardService.searchCardCount(searchCondition);
//        List<CardInfo> result = cards.stream().map(card -> new CardInfo(
//                card.getCardIdentity().getPassword(),
//                card.getName().getName(),
//                card.getName().getNameJa(),
//                card.getName().getNameEn(),
//                card.getName().getNameNw(),
//                card.getImgUrl(),
//                card.getMonster().getLevel(),
//                card.getMonster().getAttribute(),
//                card.getMonster().getRace(),
//                card.getMonster().getAtk(),
//                card.getMonster().getDef(),
//                card.getMonster().getPend(),
//                card.getMonster().getLink(),
//                card.getDescription().getDesc(),
//                card.getDescription().getDescNw(),
//                card.getDescription().getDescJa(),
//                card.getDescription().getDescEn(),
//                card.getTypeSt(),
//                card.getMonster().getLinkArrow(),
//                card.getAdjust(),
//                card.getTypeVal(),
//                card.getInclude().stream().map(Include::toString).collect(Collectors.toList())
//        )).collect(Collectors.toList());
//        return new Pageable<>(count, result);
//    }
//}
