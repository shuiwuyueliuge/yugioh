package cn.mayu.yugioh.ceres.domain.aggregate.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskStopwatch {

    private final Long startTime;

    private Long endTime;

    public TaskStopwatch(Long startTime) {
        this.startTime = startTime;
    }
}
