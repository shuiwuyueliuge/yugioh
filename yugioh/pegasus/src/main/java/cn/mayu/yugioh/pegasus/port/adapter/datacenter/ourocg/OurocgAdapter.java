package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.application.CardDTO;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgAdapter {

    public static List<CardDTO> getCardDTOList(List<Map<String, String>> mapList) {
        return mapList.stream().map(map -> {
            CardDTO card = new CardDTO();
            card.setPassword(map.get("password"));
            card.setName(map.get("name"));
            card.setNameEn(map.get("name_en"));
            card.setNameJa(map.get("name_ja"));
            card.setNameNw(map.get("name_nw"));
            card.setImgUrl(map.get("img_url"));
            card.setLevel(map.get("level"));
            card.setAttribute(map.get("attribute"));
            card.setRace(map.get("race"));
            card.setAtk(map.get("atk"));
            card.setDef(map.get("def"));
            card.setPend(map.get("pend_l"));
            card.setLink(map.get("link"));
            card.setDesc(effectFormat(map.get("desc")));
            card.setBan(map.get("locale"));
            card.setDescEn(effectFormat(map.get("desc_en")));
            card.setDescJa(effectFormat(map.get("desc_ja")));
            card.setDescNw(effectFormat(map.get("desc_nw")));
            card.setTypeVal(map.get("type_val"));
            String typeSt = map.get("type_st");
            if ("null".equals(typeSt) && typeSt.indexOf("\\|") != -1) {
                card.setTypeSt(Lists.newArrayList(typeSt.split("\\|")));
            }

            String linkArrow = map.get("link_arrow");
            if ("null".equals(linkArrow) && (linkArrow.indexOf(",") != -1 || linkArrow.indexOf("，") != -1)) {
                linkArrow = linkArrow.replace("，", ",");
                card.setLinkArrow(Lists.newArrayList(linkArrow.split(",")));
            }

            card.setCardUrl(map.get("href"));
            return card;
        }).collect(Collectors.toList());
    }

    public static String effectFormat(String str) {
        if (str == null) {
            return str;
        }

        while(true) {
            int index = str.indexOf("@#");
            if (str.indexOf("@#") == -1) {
                break;
            }

            int nextIndex = str.indexOf("@", index + 3);
            String source = str.substring(index, nextIndex + 1);
            String target = str.substring(index + 2, nextIndex);
            str = str.replace(source, target);
        }

        return str;
    }

    public static List<CardDTO.IncludeInfo> getIncludeInfo(Include include) {
        List<Include.IncludeInfo> includeInfos = include.getIncludeInfos();
        return includeInfos.stream().map(data ->
            new CardDTO.IncludeInfo(data.getPackName(),
                                    data.getSellTime(),
                                    data.getNumber(),
                                    data.getRare(),
                                    data.getShortName())
        ).collect(Collectors.toList());
    }
}
