package cn.mayu.yugioh.minerva.port.adapter.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author: YgoPlayer
 * @time: 2021/5/11 10:11 上午
 * @description: 卡片数据多线程消费配置
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "data-storm.thread")
public class DataStormReceiverThreadProperties {

	private int workerSize = 1;

	private int queueSize = 1;
}
