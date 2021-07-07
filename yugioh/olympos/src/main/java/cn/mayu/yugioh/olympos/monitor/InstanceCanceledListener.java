package cn.mayu.yugioh.olympos.monitor;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: 处理服务下线
 * @author: YgoPlayer
 * @time: 2021/5/27 2:26 下午
 */
@Component
public class InstanceCanceledListener implements ApplicationListener<EurekaInstanceCanceledEvent> {

    @Override
    public void onApplicationEvent(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        String appName = eurekaInstanceCanceledEvent.getAppName();
        String serverId = eurekaInstanceCanceledEvent.getServerId();
        System.out.println("module canceled server_id -> " + serverId + ", appName -> " + appName + " " + new Date().toGMTString());
    }
}
