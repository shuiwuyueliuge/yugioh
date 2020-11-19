package cn.mayu.yugioh.pegasus.application.datacenter;

import cn.mayu.yugioh.pegasus.application.exception.DataCenterFindException;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.OurocgDataCenter;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;

@Component
public class DataCenterStrategy {

    private static final Map<DataCenterEnum, DataCenter> DATA_CENTER_MAP;

    static {
        DATA_CENTER_MAP = Maps.newHashMap();
    }

    public DataCenterStrategy() {
        initDataCenter();
    }

    private void initDataCenter() {
        DATA_CENTER_MAP.put(DataCenterEnum.OUROCG, new OurocgDataCenter("", "", Boolean.TRUE));
    }

    public DataCenter findDataCenter(String dataCenterStr) {
        Optional<DataCenterEnum> optionalDataCenterEnum = DataCenterEnum.findDataCenterEnum(dataCenterStr);
        if (!optionalDataCenterEnum.isPresent()) {
            throw new DataCenterFindException(dataCenterStr + " not found");
        }

        DataCenter dataCenter = DATA_CENTER_MAP.get(optionalDataCenterEnum.get());
        if (!dataCenter.exists()) {
            throw new DataCenterFindException(dataCenter + " not exists");
        }

        return dataCenter;
    }
}
