package cn.mayu.yugioh.ceres;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import java.time.Duration;

/**
 * @description: 卡片数据服务
 * 德墨忒尔 克瑞斯 Demeter/Ceres 谷物与丰饶
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
@EnableFeignClients(basePackages = {"cn.mayu.yugioh.common.facade.hermes"})
@SpringBootApplication
public class Ceres {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Ceres.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> slowCustomizer() {
        return factory -> factory.configure(builder -> builder.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build()), "EventFacade#receiveEvent(EventReceiveCommand)");
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer(MeterRegistry meterRegistry) {
        return meterRegistry1 -> meterRegistry.config().commonTags("application", "Tenantapp");
    }
}

/**
 * -javaagent:/Users/a../Downloads/apache-skywalking-apm-bin/agent/skywalking-agent.jar
 * -Dskywalking_config=/Users/a../Downloads/apache-skywalking-apm-bin/agent/config/agent.config
 * -Dskywalking.agent.service_name=ceres
 */