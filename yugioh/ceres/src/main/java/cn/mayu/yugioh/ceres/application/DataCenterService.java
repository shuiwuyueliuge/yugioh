package cn.mayu.yugioh.ceres.application;

import cn.mayu.yugioh.ceres.application.dto.SyncCardResult;
import cn.mayu.yugioh.ceres.domain.aggregate.task.Task;
import reactor.core.publisher.Flux;

/**
 * @description: 任务服务
 * @author: YgoPlayer
 * @time: 2021/5/11 11:12 上午
 */
public interface DataCenterService {

    Flux<SyncCardResult> syncCardList(Task task);
}
