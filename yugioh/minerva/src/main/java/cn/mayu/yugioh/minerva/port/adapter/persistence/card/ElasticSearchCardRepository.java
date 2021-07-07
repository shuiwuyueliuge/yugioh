package cn.mayu.yugioh.minerva.port.adapter.persistence.card;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ElasticSearchCardRepository extends PagingAndSortingRepository<ElasticSearchCardDO, String> {

}
