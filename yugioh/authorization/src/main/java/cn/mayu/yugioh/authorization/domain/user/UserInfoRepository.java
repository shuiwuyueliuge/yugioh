package cn.mayu.yugioh.authorization.domain.user;

public interface UserInfoRepository {

    UserInfo findByUsername(String username);
}
