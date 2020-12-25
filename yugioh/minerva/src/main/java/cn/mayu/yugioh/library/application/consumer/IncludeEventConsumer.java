package cn.mayu.yugioh.library.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import cn.mayu.yugioh.library.application.pack.PackageCommandService;
import cn.mayu.yugioh.library.application.pack.command.PackCreateCommand;
import cn.mayu.yugioh.library.domain.aggregate.include.IncludeCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncludeEventConsumer implements DomainEventSubscribe<IncludeCreated> {

    @Autowired
    private PackageCommandService packageCommandService;

    @Override
    public void subscribe(IncludeCreated includeCreated) {
        PackCreateCommand packCreateCommand = new PackCreateCommand(
                includeCreated.getPackageName(),
                includeCreated.getSaleDate(),
                includeCreated.getPackShortName()
        );

        packageCommandService.createPackage(packCreateCommand);
    }

    @Override
    public Class<IncludeCreated> domainEventClass() {
        return IncludeCreated.class;
    }
}
