package cn.mayu.yugioh.pegasus.application.datacenter;

import cn.mayu.yugioh.pegasus.exception.DataCenterNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataCenterStrategy {

    private Map<DataCenterEnum, DataCenterFactory> dataCenterMap;

    public DataCenterStrategy(DataCenterConfiguration configuration,
                              Set<DataCenterFactory> dataCenters) {
        initDataCenter(configuration, dataCenters);
    }

    /**
     * 配置中心获取进行数据中心的初始化
     */
    private void initDataCenter(DataCenterConfiguration configuration, Set<DataCenterFactory> dataCenters) {
        List<DataCenterConfiguration.DataCenterProperty> properties = configuration.getProperty();
        Map<DataCenterEnum, DataCenterConfiguration.DataCenterProperty> propertyMap = properties.stream().filter(data -> DataCenterEnum.findDataCenterEnum(data.getDataType()).isPresent())
                .collect(Collectors.toMap(data -> DataCenterEnum.findDataCenterEnum(data.getDataType()).get(),
                        Function.identity(),
                        (a, b) -> a));
        dataCenterMap = dataCenters.stream().map(data -> {
            DataCenterConfiguration.DataCenterProperty property = propertyMap.get(data.type());
            AbstractDataCenterFactory dataCenter = (AbstractDataCenterFactory) data;
            dataCenter.setProperty(property);
            return data;
        }).collect(Collectors.toMap(data -> data.type(), Function.identity(), (a, b) -> a));
    }

    public DataCenterFactory findDataCenter(DataCenterEnum dataCenterEnum) {
        DataCenterFactory dataCenter = dataCenterMap.get(dataCenterEnum);
        if (!dataCenter.exists()) {
            throw new DataCenterNotFoundException(dataCenter.type() + " not exists");
        }

        return dataCenter;
    }
}
