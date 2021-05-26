package cn.mayu.yugioh.minerva.port.adapter.persistence.packages;

import cn.mayu.yugioh.minerva.domain.aggregate.card.CardIdentity;
import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackage;
import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackageIdentity;
import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CardPackageRepositoryImpl implements CardPackageRepository {

    @Autowired
    private JpaCardPackageRepository jpaCardPackageRepository;

    @Override
    public synchronized void store(CardPackage body) {
        PackageDO packageDO = new PackageDO(
                body.getPackageIdentity().getPackageName(),
                body.getSaleDate(),
                body.getPackageIdentity().getPackShortName(),
                body.getVolume(),
                body.getCards().stream().map(CardIdentity::getPassword).collect(Collectors.joining(","))
        );

        PackageDO saved = jpaCardPackageRepository.findByPackageName(body.getPackageIdentity().getPackageName());
        if (saved != null) {
            packageDO.setId(saved.getId());
        }

        jpaCardPackageRepository.save(packageDO);
    }

    @Override
    public CardPackage findByIdentity(CardPackageIdentity cardPackageIdentity) {
        PackageDO packageDO = jpaCardPackageRepository.findByPackageName(cardPackageIdentity.getPackageName());
        if (Objects.isNull(packageDO)) {
            return null;
        }

        return new CardPackage(
                cardPackageIdentity,
                packageDO.getSaleDate(),
                packageDO.getVolume(),
                Stream.of(packageDO.getCards().split(",")).map(CardIdentity::new).collect(Collectors.toSet())
        );
    }
}
