package cn.mayu.yugioh.hermes.domain.aggregate;

/**
 * @description: 事件溯源服务
 * @author: YgoPlayer
 * @time: 2021/5/17 2:23 下午
 */
public interface EventSourcingService {

    void publish(EventSourcingCreated created);
}