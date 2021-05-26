package cn.mayu.yugioh.ceres.domain.aggregate.task;

/**
 * @description: 任务状态枚举
 *               就绪 -> 执行 -> 暂停 -> 完成/结束
 * @author: YgoPlayer
 * @time: 2021/5/11 11:12 上午
 */
public enum TaskStatusEnum {

    READY, RUNNING, COMPLETE;
}
