//package cn.mayu.yugioh.common.facade.minerva;
//
//import cn.mayu.yugioh.common.facade.minerva.model.CardQueryListRequest;
//import cn.mayu.yugioh.common.facade.minerva.model.CardInfo;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(name = "minerva", contextId = "card"
//        //, fallbackFactory = CardSourceFacadeFallbackFactory.class
//)
//public interface CardFacade {
//
//    /**
//     * 查询卡片列表
//     *
//     * @param cardRequest card查询条件
//     * @return 卡片列表
//     */
//    @PostMapping("/card")
//    Pageable<CardInfo> searchCardList(@RequestBody CardQueryListRequest cardRequest);
//}
