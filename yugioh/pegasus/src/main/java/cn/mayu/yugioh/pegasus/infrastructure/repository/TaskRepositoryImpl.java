package cn.mayu.yugioh.pegasus.infrastructure.repository;

import cn.mayu.yugioh.pegasus.domain.aggregate.DataCenterTask;
import cn.mayu.yugioh.pegasus.domain.aggregate.DataCenterTaskIdentity;
import cn.mayu.yugioh.pegasus.domain.aggregate.TaskRepository;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private JpaTaskRepository jpaTaskRepository;

    @Override
    public DataCenterTask findByDataCenterAndStatus(DataCenterEnum dataCenter, String running, String type) {
        DataCenterTaskDO dataCenterTaskDO = jpaTaskRepository.findByDataCenterAndStatusAndType(dataCenter.name(), running, type);
        if (dataCenterTaskDO == null) {
            return null;
        }

        return new DataCenterTask(new DataCenterTaskIdentity(dataCenterTaskDO.getDataCenter(), dataCenterTaskDO.getType()), dataCenterTaskDO.getOperateChannel());
    }

    @Override
    public void store(DataCenterTask dataCenterTask) {
        jpaTaskRepository.save(new DataCenterTaskDO(
                dataCenterTask.getDataCenterTaskIdentity().getDataCenter(),
                dataCenterTask.getDataCenterTaskIdentity().getStartTime(),
                dataCenterTask.getDataCenterTaskIdentity().getType(),
                dataCenterTask.getStatus(),
                dataCenterTask.getOperateChannel(),
                dataCenterTask.getEndTime()
        ));
    }
}
