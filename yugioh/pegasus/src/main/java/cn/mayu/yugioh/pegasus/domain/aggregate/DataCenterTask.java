package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.domain.Entity;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DataCenterTask extends Entity {

    private DataCenterTaskIdentity dataCenterTaskIdentity;

    private String status;

    private String operateChannel;

    private long endTime;

    public DataCenterTask(DataCenterTaskIdentity dataCenterTaskIdentity, String operateChannel) {
        this.status = DataCenterTaskStatusEnum.RUNNING.name();
        this.dataCenterTaskIdentity = dataCenterTaskIdentity;
        this.operateChannel = operateChannel;
    }

    public void finish() {
        this.endTime = System.currentTimeMillis();
        this.status = "end";
    }

    public void runCardTask() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new CardCenterTaskCreated(
                this.dataCenterTaskIdentity,
                this.operateChannel
        ));
    }

    public void runIncludeTask(String cardPassword, String resource) {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.publishEvent(new IncludeCenterTaskCreated(
                this.dataCenterTaskIdentity,
                this.operateChannel,
                cardPassword,
                resource
        ));
    }
}
