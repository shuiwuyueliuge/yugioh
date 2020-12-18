package cn.mayu.yugioh.library.application.pack;

import cn.mayu.yugioh.library.application.pack.command.PackCreateCommand;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackage;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackageIdentity;
import org.springframework.stereotype.Service;

@Service
public class PackageCommandService {

    public void createPackage(PackCreateCommand packCreateCommand) {
        CardPackageIdentity cardPackageIdentity = new CardPackageIdentity(packCreateCommand.getPackageName(), packCreateCommand.getPackShortName());
        CardPackage cardPackage = new CardPackage(cardPackageIdentity, packCreateCommand.getSaleDate());
        cardPackage.commitTo();
    }
}
