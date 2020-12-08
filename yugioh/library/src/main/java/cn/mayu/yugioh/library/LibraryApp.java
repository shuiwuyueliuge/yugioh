package cn.mayu.yugioh.library;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LibraryApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
