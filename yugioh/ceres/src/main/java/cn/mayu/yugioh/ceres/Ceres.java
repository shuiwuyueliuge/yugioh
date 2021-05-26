package cn.mayu.yugioh.ceres;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import java.util.stream.Collectors;

/**
 * @description: 卡片数据服务
 * 德墨忒尔 克瑞斯 Demeter/Ceres 谷物与丰饶
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
@EnableFeignClients(basePackages = {
        "cn.mayu.yugioh.common.facade.hermes",
        "cn.mayu.yugioh.common.facade.minerva"
})
@SpringBootApplication
public class Ceres {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Ceres.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}



