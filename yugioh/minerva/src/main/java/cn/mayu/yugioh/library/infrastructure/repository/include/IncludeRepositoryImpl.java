package cn.mayu.yugioh.library.infrastructure.repository.include;

import cn.mayu.yugioh.library.domain.aggregate.include.Include;
import cn.mayu.yugioh.library.domain.aggregate.include.IncludeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncludeRepositoryImpl implements IncludeRepository {

    @Autowired
    private JpaIncludeRepository jpaIncludeRepository;

    @Override
    public synchronized void store(Include include) {
        IncludeDO includeDO = new IncludeDO(
                include.getPackageIdentity().getPackageName(),
                include.getIncludeTime(),
                include.getSerial(),
                include.getRare(),
                include.getPackageIdentity().getPackShortName(),
                include.getCardIdentity().getPassword()
        );

        IncludeDO saved = jpaIncludeRepository.findByPackageNameAndPasswordAndRare(
                include.getPackageIdentity().getPackageName(),
                include.getCardIdentity().getPassword(),
                include.getRare()
        );

        if (saved != null) {
            includeDO.setId(saved.getId());
        }

        jpaIncludeRepository.save(includeDO);
    }
}
