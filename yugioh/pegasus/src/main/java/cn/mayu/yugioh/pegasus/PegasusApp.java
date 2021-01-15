package cn.mayu.yugioh.pegasus;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"cn.mayu.yugioh.common.facade.postman"})
@SpringBootApplication
public class PegasusApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PegasusApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
