package cn.mayu.yugioh.library;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"cn.mayu.yugioh.common.facade.postman"})
@SpringBootApplication
public class MinervaApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MinervaApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
