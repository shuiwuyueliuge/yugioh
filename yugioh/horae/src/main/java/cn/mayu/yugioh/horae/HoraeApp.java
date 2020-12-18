package cn.mayu.yugioh.horae;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HoraeApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HoraeApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
