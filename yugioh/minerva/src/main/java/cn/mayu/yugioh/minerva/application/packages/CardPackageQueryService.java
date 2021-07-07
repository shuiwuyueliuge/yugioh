//package cn.mayu.yugioh.minerva.application.packages;
//
//import cn.mayu.yugioh.common.web.handler.Pageable;
//import cn.mayu.yugioh.minerva.application.RestCode;
//import cn.mayu.yugioh.minerva.application.exception.DataCenterNotFoundException;
//import cn.mayu.yugioh.minerva.domain.aggregate.card.CardIdentity;
//import cn.mayu.yugioh.minerva.domain.aggregate.datacenter.DataCenterHolder;
//import cn.mayu.yugioh.minerva.domain.aggregate.datacenter.DataCenterService;
//import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackage;
//import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * 卡包查询服务
// */
//@Service
//public class CardPackageQueryService {
//
//    @Autowired
//    private DataCenterHolder centerHolder;
//
//    /**
//     * 查询卡包列表
//     *
//     * @param dataCenter 数据来源
//     * @param page       当前页数
//     * @param pageSize   每页大小
//     * @return 卡包列表
//     */
//    public Pageable<CardPackageDTO> searchCardPackageList(String dataCenterId, int page, int pageSize) {
//        DataCenterService dataCenterService = centerHolder.find(dataCenterId);
//        if (Objects.isNull(dataCenterService)) {
//            throw new DataCenterNotFoundException(RestCode.CARD.code, "dataCenter not development completed");
//        }
//
//        CardPackageService cardPackageService = dataCenterService.initCardPackageService();
//        Long count = cardPackageService.searchPackageCount();
//        if (count != null && count == 0) {
//            throw new DataCenterNotFoundException(RestCode.CARD.code, "no package found");
//        }
//
//        Collection<CardPackage> packages = cardPackageService.searchCardPackageList(page, pageSize);
//        List<CardPackageDTO> packageList = packages.stream().map(pack ->
//                new CardPackageDTO(pack.getPackageIdentity().getPackageName(),
//                pack.getPackageIdentity().getPackShortName(),
//                pack.getSaleDate(),
//                pack.getVolume(),
//                pack.getCards().stream().map(CardIdentity::getPassword).collect(Collectors.toSet()))).collect(Collectors.toList());
//        return new Pageable<>(count, packageList);
//    }
//}
