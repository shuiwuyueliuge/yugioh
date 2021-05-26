package cn.mayu.yugioh.prometheus.port.adapter.stomp;

import org.springframework.context.ApplicationEvent;

/**
 * @description: 队列订阅完成事件
 * @author: YgoPlayer
 * @time: 2021/5/26 3:25 下午
 */
public class BrokerSubFinishedEvent extends ApplicationEvent {

    public BrokerSubFinishedEvent(Object source) {
        super(source);
    }
}
