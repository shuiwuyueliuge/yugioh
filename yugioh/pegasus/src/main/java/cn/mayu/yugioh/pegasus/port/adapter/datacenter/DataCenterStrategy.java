package cn.mayu.yugioh.pegasus.port.adapter.datacenter;

import cn.mayu.yugioh.pegasus.exception.DataCenterFindException;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.ourocg.OurocgDataCenter;
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

    /**
     * 配置中心获取进行数据中心的初始化
     */
    private void initDataCenter() {
        // TODO 模拟
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
