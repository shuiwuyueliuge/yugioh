package cn.mayu.yugioh.authorization.port.adapter.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<ClientDO, Long> {

    ClientDO findByClientId(String clientId);
}
