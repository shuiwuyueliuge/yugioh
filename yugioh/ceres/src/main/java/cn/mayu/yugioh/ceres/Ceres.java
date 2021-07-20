package cn.mayu.yugioh.ceres;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @description: 卡片数据服务
 * 德墨忒尔 克瑞斯 Demeter/Ceres 谷物与丰饶
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
@SpringBootApplication
public class Ceres {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Ceres.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer(MeterRegistry meterRegistry) {
        return registry -> meterRegistry.config().commonTags("application", Ceres.class.getName());
    }
}

/**
 * -javaagent:/Users/a../Downloads/apache-skywalking-apm-bin/agent/skywalking-agent.jar
 * -Dskywalking_config=/Users/a../Downloads/apache-skywalking-apm-bin/agent/config/agent.config
 * -Dskywalking.agent.service_name=ceres
 */