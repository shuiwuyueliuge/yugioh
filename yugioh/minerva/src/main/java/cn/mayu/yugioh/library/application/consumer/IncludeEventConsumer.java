package cn.mayu.yugioh.library.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.library.application.pack.PackageCommandService;
import cn.mayu.yugioh.library.application.pack.command.PackCreateCommand;
import cn.mayu.yugioh.library.domain.aggregate.include.Include;
import cn.mayu.yugioh.library.domain.aggregate.include.IncludeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncludeEventConsumer implements DomainEventConsumer<Include> {

    @Autowired
    private IncludeRepository includeRepository;

    @Autowired
    private PackageCommandService packageCommandService;

    @Override
    public void subscribe(DomainEvent<Include> domainEvent) {
        Include include = domainEvent.getBody();
        includeRepository.store(include);
        PackCreateCommand packCreateCommand = new PackCreateCommand(include.getPackageIdentity().getPackageName(),
                include.getIncludeTime(),
                include.getPackageIdentity().getPackShortName(), domainEvent.getSource().toString());
        packageCommandService.createPackage(packCreateCommand);
    }

    @Override
    public String getEventType() {
        return "include";
    }
}
