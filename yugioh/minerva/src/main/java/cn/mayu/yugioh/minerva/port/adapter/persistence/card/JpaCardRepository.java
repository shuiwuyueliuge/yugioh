package cn.mayu.yugioh.minerva.port.adapter.persistence.card;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCardRepository extends JpaRepository<CardDO, Long> {

    CardDO findByPassword(String password);
}
