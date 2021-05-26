package cn.mayu.yugioh.minerva;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: 卡片数据服务
 * 雅典娜 密涅瓦 Athena/Minerva/Bellona 智慧与学问
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
@EnableFeignClients(basePackages = { "cn.mayu.yugioh.common.facade.hermes" })
@SpringBootApplication
public class Minerva {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Minerva.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}