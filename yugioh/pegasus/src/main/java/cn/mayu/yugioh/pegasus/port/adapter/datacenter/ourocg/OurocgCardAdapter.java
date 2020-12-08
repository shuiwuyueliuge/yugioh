package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.domain.aggregate.*;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgCardAdapter {

    public static List<MetaData> getCardDTOList(List<Map<String, String>> mapList) {
        return mapList.stream().map(data -> {
            data.put("password", data.get("password").replace("null", ""));
            if (StringUtils.isEmpty(data.get("password"))) {
                data.put("password", SnowFlake.nextId() + "");
            }

            MetaDataIdentity metaDataIdentity = new MetaDataIdentity(data.get("password"), DataCenterEnum.OUROCG, "card");
            return new MetaData(metaDataIdentity, JsonConstructor.defaultInstance().writeValueAsString(data));
        }).collect(Collectors.toList());
    }
}
