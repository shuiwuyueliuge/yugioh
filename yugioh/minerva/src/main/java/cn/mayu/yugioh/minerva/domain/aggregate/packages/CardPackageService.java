package cn.mayu.yugioh.minerva.domain.aggregate.packages;

import java.util.Collection;

public interface CardPackageService {

    /**
     * 查询卡包列表
     *
     * @param page       当前页数
     * @param pageSize   每页大小
     * @return 卡包列表
     */
    Collection<CardPackage> searchCardPackageList(int page, int pageSize);

    /**
     * 获取卡包总数
     *
     * @return 卡包总数
     */
    Long searchPackageCount();
}
