package cn.mayu.yugioh.library.infrastructure.repository.card;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCardRepository extends JpaRepository<CardDO, Long> {

//    CardDO findByPassword(String password);
}
