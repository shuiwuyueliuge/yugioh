package cn.mayu.yugioh.postman.interfaces.task;

import cn.mayu.yugioh.postman.application.EventCommandService;
import cn.mayu.yugioh.postman.application.EventQueryService;
import cn.mayu.yugioh.postman.domain.aggregate.EventSourcing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.OptionalLong;

@Component
public class EventSourcingTask {

    @Autowired
    private EventQueryService eventQueryService;

    @Autowired
    private EventCommandService eventCommandService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String serviceId;

    @Scheduled(cron = "${event.exec.corn}")
    public void getEvent() {
        // 注册中心获取ip列表
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
        String local = getLocalIp();
        long localLong = ipToNumber(local);
        OptionalLong min = serviceInstances.stream().mapToLong(data ->
            ipToNumber(((EurekaServiceInstance) data).getInstanceInfo().getIPAddr())
        ).min();
        if (!min.isPresent() || min.getAsLong() != localLong) {
            return;
        }

        List<EventSourcing> eventSourcing = eventQueryService.findByStatus(0, 0, 100);
        eventSourcing.forEach(eventCommandService::publishEvent);
    }

    private String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }

    private Long ipToNumber(String ip) {
        Long ipLong = 0L;
        String[] ipNumbers = ip.split("\\.");
        for (String ipNumber : ipNumbers) {
            ipLong = ipLong << 8 | Integer.parseInt(ipNumber);
        }

        return ipLong;
    }
}
