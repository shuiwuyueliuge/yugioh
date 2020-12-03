package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.domain.aggregate.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgCardAdapter {

    public static List<MetaData> getCardDTOList(List<Map<String, String>> mapList) {
        return mapList.stream().map(data -> {
            MetaDataIdentity metaDataIdentity = new MetaDataIdentity(DataCenterEnum.OUROCG, "card");
            return new MetaData(metaDataIdentity, JsonConstructor.defaultInstance().writeValueAsString(data));
        }).collect(Collectors.toList());
    }
}
