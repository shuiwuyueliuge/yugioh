package cn.mayu.yugioh.library.port.adapter.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "mq.pool")
public class MqThreadPoolConfig {

	private int count = 2;

	private int queueSize = 2;
	
	private String threadNamePrefix = "library-async-thread-";
}
