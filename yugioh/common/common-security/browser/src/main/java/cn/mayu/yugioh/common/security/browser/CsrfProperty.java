package cn.mayu.yugioh.common.security.browser;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: csrf相关配置
 * @author: YgoPlayer
 * @time: 2021/6/25 1:50 下午
 */
@Data
@ConfigurationProperties(prefix = "spring.security.browser.csrf")
public class CsrfProperty {

    private boolean csrfDisable = true;

    private String csrfIgnoringAnt;//, split
}
