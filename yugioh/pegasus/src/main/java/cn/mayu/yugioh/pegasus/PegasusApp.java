package cn.mayu.yugioh.pegasus;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PegasusApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PegasusApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
