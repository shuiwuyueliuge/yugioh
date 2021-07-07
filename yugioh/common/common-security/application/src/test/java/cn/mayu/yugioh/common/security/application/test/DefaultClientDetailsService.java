package cn.mayu.yugioh.common.security.application.test;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/29 11:51 上午
 */
public class DefaultClientDetailsService implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return new BaseClientDetails();
    }
}
