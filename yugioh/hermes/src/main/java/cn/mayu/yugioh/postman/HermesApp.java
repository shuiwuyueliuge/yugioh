package cn.mayu.yugioh.postman;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HermesApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HermesApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}