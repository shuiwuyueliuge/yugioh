package cn.mayu.yugioh.pegasus.domain.aggregate;

import lombok.Getter;

@Getter
public class DataCenterTaskIdentity {

    private String dataCenter;

    private long startTime;

    private String type;

    public DataCenterTaskIdentity(String dataCenter, String type) {
        this.dataCenter = dataCenter;
        this.type = type;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("%s_%s_%s", dataCenter, startTime, type);
    }
}
