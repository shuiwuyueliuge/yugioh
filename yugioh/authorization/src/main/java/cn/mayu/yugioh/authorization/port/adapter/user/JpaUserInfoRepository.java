package cn.mayu.yugioh.authorization.port.adapter.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserInfoRepository extends JpaRepository<UserDO, Long> {

    UserDO findByUsername(String username);
}
