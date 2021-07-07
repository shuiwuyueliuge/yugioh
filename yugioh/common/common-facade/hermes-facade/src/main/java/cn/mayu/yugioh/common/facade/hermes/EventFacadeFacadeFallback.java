package cn.mayu.yugioh.common.facade.hermes;

import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/5/27 5:45 下午
 */
public class EventFacadeFacadeFallback implements EventFacade {

    @Override
    public void receiveEvent(EventReceiveCommand eventReceiveCommand) {
        System.out.println(1111111);
    }
}
