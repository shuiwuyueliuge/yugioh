package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.pegasus.application.datacenter.IncludeData;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgIncludeDataCenter implements IncludeData {

    @Override
    public List<IncludeDTO> data2IncludeDTO(String cardData) {
        List<Map<String, String>> map = JsonParser.defaultInstance().parseJsonArray2Map(cardData);
        return map.stream().map(data -> new IncludeDTO(
                data.get("packName"),
                data.get("sellTime"),
                data.get("number"),
                data.get("rare"),
                data.get("shortName")
                )).collect(Collectors.toList());
    }
}
