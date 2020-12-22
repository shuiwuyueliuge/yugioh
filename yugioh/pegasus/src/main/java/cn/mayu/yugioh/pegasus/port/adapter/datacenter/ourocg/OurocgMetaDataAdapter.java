package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.tool.HashGenerator;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.application.datacenter.EventEnum;
import cn.mayu.yugioh.pegasus.domain.aggregate.*;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgMetaDataAdapter {

    public static List<MetaData> changeCard2MetaData(List<Map<String, String>> mapList) {
        return mapList.stream().map(data -> {
            data.put("password", data.get("password").replace("null", ""));
            if (StringUtils.isEmpty(data.get("password"))) {
                data.put("password", HashGenerator.createHashStr(data.get("name")));
            }

            MetaDataIdentity metaDataIdentity = new MetaDataIdentity(
                    data.get("password"), DataCenterEnum.OUROCG.toString(), EventEnum.CARD.getType());
            return new MetaData(metaDataIdentity, JsonConstructor.defaultInstance().writeValueAsString(data));
        }).collect(Collectors.toList());
    }

    public static MetaData changeInclude2MetaData(String password, Object includeStr) {
        MetaDataIdentity metaDataIdentity = new MetaDataIdentity
                (password, DataCenterEnum.OUROCG.toString(), EventEnum.INCLUDE.getType());
        return new MetaData(metaDataIdentity, JsonConstructor.defaultInstance().writeValueAsString(includeStr));
    }
}
