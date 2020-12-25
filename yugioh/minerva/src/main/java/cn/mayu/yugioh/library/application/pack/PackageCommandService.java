package cn.mayu.yugioh.library.application.pack;

import cn.mayu.yugioh.library.application.pack.command.PackCreateCommand;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackage;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackageIdentity;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageCommandService {

    @Autowired
    private CardPackageRepository cardPackageRepository;

    public void createPackage(PackCreateCommand packCreateCommand) {
        CardPackageIdentity cardPackageIdentity = new CardPackageIdentity(packCreateCommand.getPackageName(), packCreateCommand.getPackShortName());
        CardPackage cardPackage = new CardPackage(cardPackageIdentity, packCreateCommand.getSaleDate());
        cardPackageRepository.store(cardPackage);
    }
}
