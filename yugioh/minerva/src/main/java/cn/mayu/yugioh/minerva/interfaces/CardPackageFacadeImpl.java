//package cn.mayu.yugioh.minerva.interfaces;
//
//import cn.mayu.yugioh.common.web.result.Pageable;
//import cn.mayu.yugioh.common.web.result.ResponseBodyWrapper;
//import cn.mayu.yugioh.minerva.application.packages.CardPackageDTO;
//import cn.mayu.yugioh.minerva.application.packages.CardPackageQueryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 卡包接口服务
// */
//@ResponseBodyWrapper
//@RestController
//public class CardPackageFacadeImpl {
//
//    @Autowired
//    private CardPackageQueryService cardPackageQueryService;
//
//    /**
//     * 查询卡包列表
//     *
//     * @param dataCenter 数据来源
//     * @param page       当前页数
//     * @param pageSize   每页大小
//     * @return 卡包列表
//     */
//    @RequestMapping("/package")
//    public Pageable<CardPackageDTO> searchCardPackageList(String dataCenter, int page, int pageSize) {
//        return cardPackageQueryService.searchCardPackageList(dataCenter, page, pageSize);
//    }
//}
