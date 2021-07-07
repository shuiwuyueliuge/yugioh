package cn.mayu.yugioh.olympos;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Olympos {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Olympos.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}

/**
 * -javaagent:/Users/a../Downloads/apache-skywalking-apm-bin/agent/skywalking-agent.jar
 * -Dskywalking_config=/Users/a../Downloads/apache-skywalking-apm-bin/agent/config/agent.config
 * -Dskywalking.agent.service_name=olympos
 */
