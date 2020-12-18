package cn.mayu.yugioh.library.infrastructure.repository.include;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIncludeRepository extends JpaRepository<IncludeDO, Long> {

    IncludeDO findByPackageNameAndPasswordAndRare(String packageName, String password, String rare);
}
