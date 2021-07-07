package cn.mayu.yugioh.common.rpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/6/8 1:00 下午
 */
@Data
@ConfigurationProperties(prefix = "feign")
public class FeignProperties {

    private Retry retry;

    @Data
    public static class Retry {

        private int maxAttempts = 0; // 发起当前请求的时间间隔,单位毫秒
        private long period = 0; // 发起当前请求的最大时间间隔,单位毫秒
        private long maxPeriod = 1; // 重试次数是1，因为包括第一次，所以我们如果想要重试2次，就需要设置为3

        public boolean check() {
            return this.maxAttempts != 0 && this.period != 0 && this.maxAttempts != 1;
        }
    }
}
