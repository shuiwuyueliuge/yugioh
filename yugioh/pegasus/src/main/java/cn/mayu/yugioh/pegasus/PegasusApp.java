package cn.mayu.yugioh.pegasus;

import cn.mayu.yugioh.pegasus.application.DataCenterCommandService;
import cn.mayu.yugioh.pegasus.application.command.CardInfoCreateCommand;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PegasusApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PegasusApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Autowired
    private DataCenterCommandService aaa;

    @Override
    public void run(String... args) throws Exception {
       aaa.createCardList(new CardInfoCreateCommand(DataCenterEnum.OUROCG.name()));
    }
}


// 分布式定时任务
//    public static void main(String[] args) {
//        System.out.println(ipToNumber("192.68.0.1"));
//        System.out.println(ipToNumber("192.189.2.1"));
//        System.out.println(ipToNumber("10.189.2.1"));
//        System.out.println(ipToNumber("10.10.2.1"));
//    }
//
//    public static Long ipToNumber(String ip) {
//        Long ipLong = 0L;
//        String[] ipNumbers = ip.split("\\.");
//        for (String ipNumber : ipNumbers) {
//            ipLong = ipLong << 8 | Integer.parseInt(ipNumber);
//        }
//        return ipLong;
//    }
