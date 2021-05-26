package cn.mayu.yugioh.ceres.domain.aggregate.task;

import java.util.List;

/**
 * @description: 任务仓储抽象
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
public interface TaskRepository {

    void store(Task dataCenterTask);

    List<Task> findByIpPortAndStatus(String uuid, TaskStatusEnum ready, int offset, int row);

    Task findByDataCenterAndUuid(String dataCenter, String uuid);
}
