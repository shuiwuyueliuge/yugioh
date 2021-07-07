//package cn.mayu.yugioh.ceres.application.card;
//
//import cn.mayu.yugioh.common.facade.minerva.CardFacade;
//import cn.mayu.yugioh.common.facade.minerva.model.CardQueryListRequest;
//import cn.mayu.yugioh.common.facade.minerva.model.CardInfo;
//import cn.mayu.yugioh.common.web.handler.Pageable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//@Service
//public class CardQueryService {
//
//    @Autowired
//    private CardFacade cardFacade;
//
//    public Mono<Pageable<CardInfo>> findCardList(CardQueryListRequest cardRequest) {
//        return Mono.fromCallable(() -> cardFacade.searchCardList(cardRequest)).subscribeOn(Schedulers.boundedElastic());
//    }
//}
