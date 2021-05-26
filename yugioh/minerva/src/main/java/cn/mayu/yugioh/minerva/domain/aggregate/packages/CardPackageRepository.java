package cn.mayu.yugioh.minerva.domain.aggregate.packages;

public interface CardPackageRepository {

    void store(CardPackage body);

    CardPackage findByIdentity(CardPackageIdentity cardPackageIdentity);
}
