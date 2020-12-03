package cn.mayu.yugioh.pegasus;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class PegasusApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PegasusApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public SchedulingConfigurer init() {
        return taskRegistrar -> taskRegistrar.setScheduler(Executors.newScheduledThreadPool(2));
    }
}
