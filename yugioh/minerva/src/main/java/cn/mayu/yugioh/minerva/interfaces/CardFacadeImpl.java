//package cn.mayu.yugioh.minerva.interfaces;
//
//import cn.mayu.yugioh.common.facade.minerva.model.CardQueryListRequest;
//import cn.mayu.yugioh.common.facade.minerva.model.CardInfo;
//import cn.mayu.yugioh.common.facade.minerva.CardFacade;
//import cn.mayu.yugioh.common.web.handler.Pageable;
//import cn.mayu.yugioh.common.web.result.ResponseBodyWrapper;
//import cn.mayu.yugioh.minerva.application.card.CardQueryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 1.查询卡包，卡，禁卡表列表，同步卡包/卡/禁卡表数据
// * 2.单卡包，单卡，禁卡表同步
// * 3.卡包，卡，禁卡表全量同步
// */
//
///**
// * 卡片接口服务
// */
//@ResponseBodyWrapper
//@RestController
//public class CardFacadeImpl implements CardFacade {
//
//    @Autowired
//    private CardQueryService cardQueryService;
//
//    /**
//     * 查询卡片列表
//     *
//     * @param cardRequest card查询条件
//     * @return 卡片列表
//     */
//    @PostMapping("/card")
//    public Pageable<CardInfo> searchCardList(@RequestBody CardQueryListRequest cardRequest) {
//         return cardQueryService.searchCardList(cardRequest.getDataCenter(), cardRequest.getSearchCondition(), cardRequest.getPage(), cardRequest.getPageSize());
//    }
//}
