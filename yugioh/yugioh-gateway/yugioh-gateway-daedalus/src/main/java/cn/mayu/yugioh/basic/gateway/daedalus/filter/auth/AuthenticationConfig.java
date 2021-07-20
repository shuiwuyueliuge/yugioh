package cn.mayu.yugioh.basic.gateway.daedalus.filter.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;

/**
 * @description: 鉴权配置
 * @author: YgoPlayer
 * @time: 2021/7/12 5:32 下午
 */
@Data
public class AuthenticationConfig {

    private TokenType tokenType;

    @AllArgsConstructor
    protected enum TokenType {

        BEARER_JWT("bearer-jwt") {

            @Override
            public GatewayFilter getFilter() {
                return new BearerJwtTokenFilter();
            }

        },

        NONE("none") {

            @Override
            public GatewayFilter getFilter() {
                return (exchange, chain) -> chain.filter(exchange);
            }

        },

        UUID("uuid") {

            @Override
            public GatewayFilter getFilter() {
                return (exchange, chain) -> chain.filter(exchange);
            }

        };

        public GatewayFilter getFilter() {
            throw new IllegalArgumentException();
        }

        final String type;
    }
}
