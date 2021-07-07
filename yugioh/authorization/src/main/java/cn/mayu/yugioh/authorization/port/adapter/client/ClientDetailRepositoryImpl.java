package cn.mayu.yugioh.authorization.port.adapter.client;

import cn.mayu.yugioh.authorization.domain.client.ClientDetail;
import cn.mayu.yugioh.authorization.domain.client.ClientDetailRepository;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: 对client相关操作
 *
 * @author: YgoPlayer
 * @time: 2021/6/29 2:26 下午
 */
@Component
@AllArgsConstructor
public class ClientDetailRepositoryImpl implements ClientDetailRepository {

    private final JpaClientRepository jpaClientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDO clientDO = jpaClientRepository.findByClientId(clientId);
        if (Objects.isNull(clientDO)) {
            return null;
        }

        BaseClientDetails baseClientDetails = new BaseClientDetails(
                clientDO.getClientId(),
                clientDO.getResourceIds(),
                clientDO.getScope(),
                clientDO.getAuthorizedGrantTypes(),
                clientDO.getAuthorities(),
                clientDO.getRegisteredRedirectUris()
        );

        baseClientDetails.setClientSecret(clientDO.getClientSecret());
        baseClientDetails.setAutoApproveScopes(Objects.isNull(clientDO.getAutoApproveScopes()) ? Collections.emptyList() : Stream.of(clientDO.getAutoApproveScopes().split(",")).collect(Collectors.toList()));
        baseClientDetails.setAccessTokenValiditySeconds(clientDO.getAccessTokenValiditySeconds());
        baseClientDetails.setRefreshTokenValiditySeconds(clientDO.getRefreshTokenValiditySeconds());
        baseClientDetails.setAdditionalInformation(Objects.isNull(clientDO.getAdditionalInformation()) ? Maps.newHashMap() : JsonParser.defaultInstance().readObjectValue(clientDO.getAdditionalInformation(), Map.class));
        return new ClientDetail(baseClientDetails);
    }

    @Override
    public ClientDetailsService build() {
        return this;
    }
}
