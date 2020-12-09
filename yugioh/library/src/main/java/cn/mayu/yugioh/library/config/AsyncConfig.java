package cn.mayu.yugioh.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

	public static final String ASYNC_EXECUTOR_NAME = "asyncExecutor";
	
	@Autowired
	private ThreadPoolTaskConfig taskConfig;

	@Bean(name = ASYNC_EXECUTOR_NAME)
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(taskConfig.getCoreSize());
		executor.setMaxPoolSize(taskConfig.getMaxSize());
		executor.setQueueCapacity(taskConfig.getQueueSize());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix(taskConfig.getThreadNamePrefix());
		executor.initialize();
		return executor;
	}
}
