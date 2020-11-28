package cn.mayu.yugioh.pegasus.task;

import cn.mayu.yugioh.pegasus.application.DataCenterCommandService;
import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.OptionalLong;
import java.util.stream.Stream;

/**
 * 获取data center数据
 */
@Component
public class DataCenterTask implements CommandLineRunner {

    @Autowired
    private DataCenterCommandService dataCenterCommandService;

    @Scheduled(cron = "${card.corn}")
    public void createCardList() {
        // 注册中心获取ip列表
        String[] ips = new String[]{ "192.168.10.38"};
        String local = getLocalIp();
        long localLong = ipToNumber(local);
        OptionalLong min = Stream.of(ips).mapToLong(this::ipToNumber).min();
        if (min.getAsLong() != localLong) {
            return;
        }

        Stream.of(DataCenterEnum.values()).parallel()
                .forEach(data -> {
                    CardInfoCreateCommand cardListCreateCommand = new CardInfoCreateCommand();
                    cardListCreateCommand.setDataCenter(data.toString());
                    dataCenterCommandService.createCardList(cardListCreateCommand);
                });
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

    @Override
    public void run(String... args) throws Exception {
        createCardList();
    }
}
