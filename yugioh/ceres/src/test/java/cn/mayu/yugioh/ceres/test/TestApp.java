package cn.mayu.yugioh.ceres.test;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}



