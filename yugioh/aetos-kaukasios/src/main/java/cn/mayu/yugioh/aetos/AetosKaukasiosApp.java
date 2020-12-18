package cn.mayu.yugioh.aetos;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AetosKaukasiosApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AetosKaukasiosApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
