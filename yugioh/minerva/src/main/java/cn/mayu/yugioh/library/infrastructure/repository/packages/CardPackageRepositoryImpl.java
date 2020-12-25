package cn.mayu.yugioh.library.infrastructure.repository.packages;

import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackage;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardPackageRepositoryImpl implements CardPackageRepository {

    @Autowired
    private JpaCardPackageRepository jpaCardPackageRepository;

    @Override
    public synchronized void store(CardPackage body) {
        PackageDO packageDO = new PackageDO(
                body.getPackageIdentity().getPackageName(),
                body.getSaleDate(),
                body.getPackageIdentity().getPackShortName()
        );

//        PackageDO saved = jpaCardPackageRepository.findByPackageName(body.getPackageIdentity().getPackageName());
//        if (saved != null) {
//            packageDO.setId(saved.getId());
//        }

        jpaCardPackageRepository.save(packageDO);
    }
}
