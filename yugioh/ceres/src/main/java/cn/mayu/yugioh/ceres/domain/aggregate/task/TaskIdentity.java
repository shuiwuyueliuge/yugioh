package cn.mayu.yugioh.ceres.domain.aggregate.task;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskIdentity {

    private final String dataCenter;

    private final String uuid;

    private final String ipPort;

    public TaskIdentity(String dataCenter, String uuid, String ipPort) {
        this.dataCenter = dataCenter;
        this.uuid = uuid;
        this.ipPort = ipPort;
    }
}
