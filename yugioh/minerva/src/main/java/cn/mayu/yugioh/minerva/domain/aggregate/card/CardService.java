package cn.mayu.yugioh.minerva.domain.aggregate.card;

import java.util.Collection;

public interface CardService {

    /**
     * 查询卡片列表
     *
     * @param searchCondition 查询条件，卡包查询，禁卡表查询
     * @param page            当前页数
     * @param pageSize        每页大小
     * @return 卡片列表
     */
    Collection<Card> searchCardList(String searchCondition, int page, int pageSize);

    /**
     * 查询卡片总数
     *
     * @param searchCondition 查询条件，卡包查询，禁卡表查询
     * @return 卡片总数
     */
    Long searchCardCount(String searchCondition);

    /**
     * 查询卡片详情
     *
     * @param searchCondition 查询条件，卡片编号或者其他唯一标识
     * @return 卡片详情
     */
    default Card searchCardInfo(String searchCondition) {
        throw new RuntimeException();
    }
}
