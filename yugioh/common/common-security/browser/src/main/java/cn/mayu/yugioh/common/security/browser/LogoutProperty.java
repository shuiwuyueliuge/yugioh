package cn.mayu.yugioh.common.security.browser;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 登出相关配置
 * @author: YgoPlayer
 * @time: 2021/6/25 1:58 下午
 */
@Data
@ConfigurationProperties(prefix = "spring.security.browser.logout")
public class LogoutProperty {

    private String logoutUrl = "/logout";
}
