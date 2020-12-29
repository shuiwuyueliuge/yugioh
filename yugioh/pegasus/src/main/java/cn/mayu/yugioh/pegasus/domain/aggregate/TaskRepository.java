package cn.mayu.yugioh.pegasus.domain.aggregate;

import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterEnum;

public interface TaskRepository {

    DataCenterTask findByDataCenterAndStatus(DataCenterEnum dataCenter, String running, String type);

    void store(DataCenterTask dataCenterTask);
}
