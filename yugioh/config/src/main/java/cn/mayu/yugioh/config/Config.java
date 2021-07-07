package cn.mayu.yugioh.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @description: 分布式配置中心
 * @author: YgoPlayer
 * @time: 2021/6/30 5:21 下午
 */
@SpringBootApplication
@EnableConfigServer
public class Config {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Config.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}