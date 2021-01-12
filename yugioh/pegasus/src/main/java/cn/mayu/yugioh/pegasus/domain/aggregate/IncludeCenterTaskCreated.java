package cn.mayu.yugioh.pegasus.domain.aggregate;

import lombok.Getter;

@Getter
public class IncludeCenterTaskCreated extends AbstractTask {

    private String cardPassword;

    private String resource;

    public IncludeCenterTaskCreated(DataCenterTaskIdentity dataCenterTaskIdentity,
                                    String operateChannel, String parentTask, String cardPassword, String resource) {
        super(dataCenterTaskIdentity, operateChannel, parentTask);
        this.cardPassword = cardPassword;
        this.resource = resource;
    }

    @Override
    public long occurredOn() {
        return getDataCenterTaskIdentity().getStartTime();
    }
}
