package cn.mayu.yugioh.ceres.port.adapter.service;

import cn.mayu.yugioh.ceres.application.dto.SyncCardResult;
import cn.mayu.yugioh.ceres.domain.aggregate.task.Task;
import cn.mayu.yugioh.ceres.application.DataCenterService;
import cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.OurocgCardService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @description: ourocg卡片中心
 * @author: YgoPlayer
 * @time: 2021/5/11 5:43 下午
 */
@Component
public class DataCenterServiceImpl implements DataCenterService {

    /**
     * 执行卡片同步任务
     *
     * @param task
     * @return 卡片数据
     */
    @Override
    public Flux<SyncCardResult> syncCardList(Task task) {
       return new OurocgCardService().searchCardList();
    }
}
