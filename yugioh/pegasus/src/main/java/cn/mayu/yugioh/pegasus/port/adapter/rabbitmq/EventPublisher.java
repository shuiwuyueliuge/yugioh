package cn.mayu.yugioh.pegasus.port.adapter.rabbitmq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterFactory;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterStrategy;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(EventOutStream.class)
public class EventPublisher {

    @Autowired
    private EventOutStream cardOutStream;

    @Autowired
    private DataCenterStrategy dataCenterStrategy;

    public boolean publish(DomainEvent domainEvent) {
        MetaData metaData = JsonParser.defaultInstance().readObjectValue(domainEvent.getBody().toString(), MetaData.class);
        DataCenterFactory dataCenterFactory = dataCenterStrategy.findDataCenter(metaData.getMetaDataIdentity().getCenterEnum());
        Object obj = null;
        if (metaData.getMetaDataIdentity().getType().equals("card")) {
            obj = dataCenterFactory.getCardData().data2CardDTO(metaData.getData());
        }

        if (metaData.getMetaDataIdentity().getType().equals("include")) {
            obj = dataCenterFactory.getIncludeData().data2IncludeDTO(metaData.getData());
        }

        return cardOutStream.eventOutput().send(MessageBuilder.withPayload(obj).build(), 3000L);
    }
}