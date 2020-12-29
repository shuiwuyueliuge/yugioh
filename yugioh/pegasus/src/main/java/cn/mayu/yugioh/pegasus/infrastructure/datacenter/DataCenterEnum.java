package cn.mayu.yugioh.pegasus.infrastructure.datacenter;

import java.util.Optional;
import java.util.stream.Stream;

public enum DataCenterEnum {

    OUROCG;

    public static Optional<DataCenterEnum> findDataCenterEnum(final String dataCenter) {
        return Stream.of(values()).filter(dataCenterEnum ->
                dataCenterEnum.toString().equals(dataCenter.toUpperCase())).findFirst();
    }
}
