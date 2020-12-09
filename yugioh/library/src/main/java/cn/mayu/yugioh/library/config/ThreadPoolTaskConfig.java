package cn.mayu.yugioh.library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "async.pool")
public class ThreadPoolTaskConfig {
	
	private int coreSize = 2;
	
	private int maxSize = 2;
	
	private int queueSize = 2;
	
	private String threadNamePrefix = "library-async-thread-";
}
