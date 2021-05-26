package cn.mayu.yugioh.minerva.application.packages;

import cn.mayu.yugioh.minerva.application.packages.command.PackCreateCommand;
import cn.mayu.yugioh.minerva.domain.aggregate.card.CardIdentity;
import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackage;
import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackageIdentity;
import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 卡包命令服务
 */
@Service
public class CardPackageCommandService {

    @Autowired
    private CardPackageRepository cardPackageRepository;

    /**
     * 创建卡包
     *
     * @param packCreateCommand 卡包数据
     */
    public void createPackage(PackCreateCommand packCreateCommand) {
        CardPackageIdentity cardPackageIdentity = new CardPackageIdentity(packCreateCommand.getPackageName(), packCreateCommand.getPackShortName());
        Set<CardIdentity> cards = packCreateCommand.getCardPasswords().stream().map(CardIdentity::new).collect(Collectors.toSet());
        CardPackage cardPackage = cardPackageRepository.findByIdentity(cardPackageIdentity);
        if (Objects.isNull(cardPackage)) {
            cardPackage = new CardPackage(cardPackageIdentity, packCreateCommand.getSaleDate(), packCreateCommand.getVolume(), cards);
        }

        cardPackage.updateCards(cards);
        cardPackageRepository.store(cardPackage);
    }
}
