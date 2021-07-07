package cn.mayu.yugioh.common.security.browser;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/25 3:13 下午
 */
@Data
@ConfigurationProperties(prefix = "spring.security.browser.session")
public class SessionProperty {

    private int maximumSessions = 1;

    private boolean maxSessionsPreventsLogin = false;

    // server.servlet.session.timeout=1

    // spring.session.store-type=none
}
