package cn.mayu.yugioh.pegasus.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTaskRepository extends JpaRepository<DataCenterTaskDO, Long> {

    DataCenterTaskDO findByDataCenterAndStatusAndType(String name, String status, String type);
}
