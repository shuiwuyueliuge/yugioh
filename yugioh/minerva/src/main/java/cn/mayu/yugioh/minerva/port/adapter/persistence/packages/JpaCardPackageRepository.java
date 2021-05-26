package cn.mayu.yugioh.minerva.port.adapter.persistence.packages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCardPackageRepository extends JpaRepository<PackageDO, Long> {

    PackageDO findByPackageName(String packageName);
}
