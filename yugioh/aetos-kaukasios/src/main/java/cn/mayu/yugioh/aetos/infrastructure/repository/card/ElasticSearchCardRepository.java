package cn.mayu.yugioh.aetos.infrastructure.repository.card;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface ElasticSearchCardRepository extends ElasticsearchCrudRepository<CardDO, String> {

}
