package cn.mayu.yugioh.common.security.browser;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 登陆相关配置
 * @author: YgoPlayer
 * @time: 2021/6/25 11:09 上午
 */
@Data
@ConfigurationProperties(prefix = "spring.security.browser.login")
public class LoginProperty {

    private String loginPage;

    private String processingUrl = "/login";

    private String usernameParameter = "username";

    private String passwordParameter = "password";

    private String failureForwardUrl; // 需要支持 post 请求

    private String successForwardUrl; // 需要支持 post 请求
}
