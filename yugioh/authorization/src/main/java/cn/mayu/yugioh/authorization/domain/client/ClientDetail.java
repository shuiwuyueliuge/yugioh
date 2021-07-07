package cn.mayu.yugioh.authorization.domain.client;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/29 11:55 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ClientDetail extends Entity implements ClientDetails {

    private final BaseClientDetails baseClientDetails;

    @Override
    public String getClientId() {
        return baseClientDetails.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return baseClientDetails.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        return baseClientDetails.isSecretRequired();
    }

    @Override
    public String getClientSecret() {
        return baseClientDetails.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return baseClientDetails.isScoped();
    }

    @Override
    public Set<String> getScope() {
        return baseClientDetails.getScope();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return baseClientDetails.getAuthorizedGrantTypes();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return baseClientDetails.getRegisteredRedirectUri();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return baseClientDetails.getAuthorities();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return baseClientDetails.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return baseClientDetails.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return baseClientDetails.isAutoApprove(scope);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return baseClientDetails.getAdditionalInformation();
    }

//    private final String clientId; // 客户端id
//
//    private final String clientSecret; // 客户端访问密匙
//
//    private final Set<String> scope = Collections.emptySet(); // 客户端申请的权限范围
//
//    private final Set<String> resourceIds = Collections.emptySet(); // 客户端所能访问的资源id集合
//
//    private final Set<String> authorizedGrantTypes = Collections.emptySet(); // 授权类型
//
//    private final Set<String> registeredRedirectUris; // 客户端重定向URI
//
//    private final Set<String> autoApproveScopes;
//
//    private final List<GrantedAuthority> authorities = Collections.emptyList(); // 客户端权限
//
//    private final Integer accessTokenValiditySeconds;
//
//    private final Integer refreshTokenValiditySeconds;
//
//    private final Map<String, Object> additionalInformation = new LinkedHashMap<>(); // 预留字段，JSON格式
}
