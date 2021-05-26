package cn.mayu.yugioh.hermes;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 卡片数据服务
 * 赫尔墨斯 墨丘利 Hermes/Mercury 情报与商业技术
 * @author: YgoPlayer
 * @time: 2021/5/17 2:12 下午
 */
@SpringBootApplication
public class Hermes {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Hermes.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}