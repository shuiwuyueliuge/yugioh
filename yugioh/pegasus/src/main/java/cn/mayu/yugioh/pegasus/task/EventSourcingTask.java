package cn.mayu.yugioh.pegasus.task;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.event.sourcing.EventStore;
import cn.mayu.yugioh.pegasus.port.adapter.rabbitmq.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Stream;

@Component
public class EventSourcingTask {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventPublisher eventPublisher;

    @Scheduled(cron = "${event.exec.corn}")
    public void getEvent() {
        // 注册中心获取ip列表
        String[] ips = new String[]{ "192.168.10.38"};
        String local = getLocalIp();
        long localLong = ipToNumber(local);
        OptionalLong min = Stream.of(ips).mapToLong(this::ipToNumber).min();
        if (min.getAsLong() != localLong) {
            return;
        }

        List<DomainEvent> events = eventStore.findByStatus(0, 0, 1000);
        events.forEach(eventPublisher::publish);
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
