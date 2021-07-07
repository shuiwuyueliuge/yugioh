package cn.mayu.yugioh.common.security.browser;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/25 2:09 下午
 */
@Data
@ConfigurationProperties(prefix = "spring.security.browser.remember-me")
public class RememberMeProperty {

    private boolean enable = false;

    private int tokenValiditySeconds = 604800; // 7 * 24 * 60 * 60
}
