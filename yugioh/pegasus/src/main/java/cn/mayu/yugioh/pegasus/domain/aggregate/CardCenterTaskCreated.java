package cn.mayu.yugioh.pegasus.domain.aggregate;

public class CardCenterTaskCreated extends AbstractTask {

    public CardCenterTaskCreated(DataCenterTaskIdentity dataCenterTaskIdentity, String operateChannel, String parentTask) {
        super(dataCenterTaskIdentity, operateChannel, parentTask);
    }

    @Override
    public long occurredOn() {
        return getDataCenterTaskIdentity().getStartTime();
    }
}
