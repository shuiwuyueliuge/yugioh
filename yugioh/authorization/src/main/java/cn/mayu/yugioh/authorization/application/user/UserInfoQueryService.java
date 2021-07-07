package cn.mayu.yugioh.authorization.application.user;

import cn.mayu.yugioh.authorization.domain.user.UserInfo;
import cn.mayu.yugioh.authorization.domain.user.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Objects;

/**
 * @description: userinfo 查询相关服务
 * @author: YgoPlayer
 * @time: 2021/6/29 11:20 上午
 */
@Service
@AllArgsConstructor
public class UserInfoQueryService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if (Objects.isNull(userInfo)) {
            throw new UsernameNotFoundException("username: [" + username + "] not found");
        }

        return new User(username,userInfo.getPassword(), Collections.emptyList());
    }
}
