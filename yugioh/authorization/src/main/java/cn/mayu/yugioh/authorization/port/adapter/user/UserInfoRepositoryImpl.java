package cn.mayu.yugioh.authorization.port.adapter.user;

import cn.mayu.yugioh.authorization.domain.user.UserInfo;
import cn.mayu.yugioh.authorization.domain.user.UserInfoId;
import cn.mayu.yugioh.authorization.domain.user.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * @description: userinfo仓储实现
 * @author: YgoPlayer
 * @time: 2021/6/29 11:26 上午
 */
@Component
@AllArgsConstructor
public class UserInfoRepositoryImpl implements UserInfoRepository {

    private final JpaUserInfoRepository jpaUserInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        UserDO userDO = jpaUserInfoRepository.findByUsername(username);
        if (Objects.isNull(userDO)) {
            return null;
        }

        return new UserInfo(new UserInfoId(userDO.getUsername()), userDO.getPassword());
    }
}
