package cn.mayu.yugioh.prometheus;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Prometheus {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Prometheus.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
