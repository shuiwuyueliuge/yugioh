package cn.mayu.yugioh.pegasus.port.adapter.datacenter;

import cn.mayu.yugioh.pegasus.exception.DataCenterNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataCenterStrategy {

    private Map<DataCenterEnum, DataCenter> dataCenterMap;

    public DataCenterStrategy(DataCenterConfiguration configuration,
                              Set<DataCenter> dataCenters) {
        initDataCenter(configuration, dataCenters);
    }

    /**
     * 配置中心获取进行数据中心的初始化
     */
    private void initDataCenter(DataCenterConfiguration configuration, Set<DataCenter> dataCenters) {
        List<DataCenterConfiguration.DataCenterProperty> properties = configuration.getProperty();
        Map<DataCenterEnum, DataCenterConfiguration.DataCenterProperty> propertyMap = properties.stream().filter(data -> DataCenterEnum.findDataCenterEnum(data.getDataType()).isPresent())
                .collect(Collectors.toMap(data -> DataCenterEnum.findDataCenterEnum(data.getDataType()).get(),
                        Function.identity(),
                        (a, b) -> a));
        dataCenterMap = dataCenters.stream().map(data -> {
            DataCenterConfiguration.DataCenterProperty property = propertyMap.get(data.type());
            AbstractDataCenter dataCenter = (AbstractDataCenter) data;
            dataCenter.setProperty(property);
            return data;
        }).collect(Collectors.toMap(data -> data.type(), Function.identity(), (a, b) -> a));
    }

    public DataCenter findDataCenter(String dataCenterStr) {
        Optional<DataCenterEnum> optionalDataCenterEnum = DataCenterEnum.findDataCenterEnum(dataCenterStr);
        if (!optionalDataCenterEnum.isPresent()) {
            throw new DataCenterNotFoundException(dataCenterStr + " not found");
        }

        DataCenter dataCenter = dataCenterMap.get(optionalDataCenterEnum.get());
        if (!dataCenter.exists()) {
            throw new DataCenterNotFoundException(dataCenter.type() + " not exists");
        }

        return dataCenter;
    }
}
