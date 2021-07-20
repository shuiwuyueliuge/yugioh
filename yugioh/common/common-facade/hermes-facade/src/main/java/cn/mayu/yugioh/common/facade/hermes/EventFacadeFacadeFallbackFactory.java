package cn.mayu.yugioh.common.facade.hermes;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/5/27 5:45 下午
 */
@EnableFeignClients(basePackages = {"cn.mayu.yugioh.common.facade.hermes"})
public class EventFacadeFacadeFallbackFactory implements FallbackFactory<EventFacade> {

    @Override
    public EventFacade create(Throwable cause) {
        cause.printStackTrace();
        return new EventFacadeFacadeFallback();
    }
}
