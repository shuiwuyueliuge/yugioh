package cn.mayu.yugioh.common.security.application;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @description: 登陆成功返回token
 * @author: YgoPlayer
 * @time: 2021/6/28 2:07 下午
 */
@AllArgsConstructor
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ClientDetailsService clientDetailsService;

    private final AuthorizationServerTokenServices tokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String clientId = request.getParameter("client_id");
        String grantType = request.getParameter("grant_type");
        if (Objects.isNull(clientId) || Objects.isNull(grantType)) {
            throw new InvalidParameterException("request no param client_id and grant_type");
        }

        OAuth2AccessToken accessToken = createToken(clientId, grantType, authentication);
        response.getWriter().write(accessToken.getValue());
    }

    private OAuth2AccessToken createToken(String clientId, String grantType, Authentication authentication) {
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), grantType);
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        return tokenServices.createAccessToken(oAuth2Authentication);
    }
}
