package cn.mayu.yugioh.pegasus.application;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.common.basic.domain.DomainEventPublisher;
import cn.mayu.yugioh.common.basic.tool.BeanManager;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import cn.mayu.yugioh.pegasus.application.datacenter.EventEnum;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DomainConsumer {

    @Autowired
    private EventFacade eventFacade;

    @Before("execution(* cn.mayu.yugioh.pegasus.application.*Service.*(..))")
    public void registerConsumer() {
        DomainEventPublisher eventPublisher = BeanManager.getBean(DomainEventPublisher.class);
        eventPublisher.register(new DomainEventConsumer() {
            @Override
            public void subscribe(DomainEvent domainEvent) {
                eventFacade.receiveEvent(new EventReceiveCommand(domainEvent));
            }

            @Override
            public boolean matchEvent(String type) {
                return EventEnum.CARD.getType().equals(type) || EventEnum.INCLUDE.getType().equals(type);
            }
        });
    }
}
