package cn.mayu.yugioh.ceres.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 任务创建命令
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateCommand {

    private String dataCenter; // 数据来源的抽象

    private String operateChannel; // websocket的通道id parentTask为空必传
}
