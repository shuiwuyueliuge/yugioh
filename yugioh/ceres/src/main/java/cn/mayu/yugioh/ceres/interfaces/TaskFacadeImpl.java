package cn.mayu.yugioh.ceres.interfaces;

import cn.mayu.yugioh.ceres.application.TaskCommandService;
import cn.mayu.yugioh.ceres.application.command.TaskCreateCommand;
import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.facade.hermes.EventFacade;
import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import cn.mayu.yugioh.common.web.handler.RestWrapController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description: 卡片数据同步任务
 * @author: YgoPlayer
 * @time: 2021/5/10 11:12 上午
 */
@Slf4j
@RestWrapController
@AllArgsConstructor
public class TaskFacadeImpl {

    private final TaskCommandService taskCommandService;

    private final EventFacade eventFacade;

    @PostMapping("/task/createTask")
    public void createTask(@RequestBody TaskCreateCommand taskCreateCommand) {
        //taskCommandService.createTask(taskCreateCommand);
        System.out.println(TraceContext.traceId());
        RemoteDomainEvent runDomainEvent = new RemoteDomainEvent(
                System.currentTimeMillis(),
                "task-progress-in-0",
                "{\"taskId\":\"" + 123 + "\",\"taskInfo\":\"" + 123 + "\"}",
                ""
        );

        eventFacade.receiveEvent(new EventReceiveCommand(runDomainEvent));
    }
}














// 查询卡片列表（卡包条件）
// 查询卡片详情

// 同步数据 -> 任务异常 -> 暂停（任务存档，需要任务进度检查）
//         -> 任务恢复（存档点执行任务）
//         -> 任务日志 （日志保存和实时推送）
// 选择平台同步，不同平台同步相同的数据没有意义暂选ourocg
// 同步卡片列表，同步单个卡片详情
// 2个业务服务 1个事件溯源服务

//             管理员                 系统            系统            管理员                 系统  ｜    系统          系统
//            卡片同步任务创建完成     任务日志创建     卡片同步任务已暂停   卡片同步任务已恢复  任务已完成   ｜ 卡片创建完成   卡包创建完成
//             创建任务                创建日志          暂停任务      恢复任务            任务完成   ｜    卡创建         卡包创建
//                 同步任务聚合           日志聚合                                                 ｜    卡聚合        卡包聚合
//                                              任务上下文                                        ｜      卡片上下文

//                                                                                                   ---->  创建卡片
//                                                                                                   |
// 后台创建任务 -> 事件溯源 -> mq创建任务 -> 获取数据 -> 任务完成事件 -> 事件溯源（command对象） -> mq 创建卡片数据 -
//                                                                                                   |
//                                                                                                   ---->   创建卡包