package cn.mayu.yugioh.library.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackage;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PackageEventConsumer implements DomainEventConsumer<CardPackage> {

    @Autowired
    private CardPackageRepository cardPackageRepository;

    @Override
    public void subscribe(DomainEvent<CardPackage> domainEvent) {
        cardPackageRepository.store(domainEvent.getBody());
    }

    @Override
    public String getEventType() {
        return "package";
    }
}
