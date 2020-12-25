package cn.mayu.yugioh.library.infrastructure.repository.packages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCardPackageRepository extends JpaRepository<PackageDO, Long> {

//    PackageDO findByPackageName(String packageName);
}
