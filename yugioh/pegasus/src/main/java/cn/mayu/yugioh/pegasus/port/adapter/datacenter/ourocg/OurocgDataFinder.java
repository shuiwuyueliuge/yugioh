package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.pegasus.application.datacenter.IncludeData;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaData;
import cn.mayu.yugioh.pegasus.application.datacenter.CardData;
import cn.mayu.yugioh.common.basic.html.HtmlHandler;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgDataFinder implements CardData, IncludeData, Iterator<List<MetaData>> {

    private final HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private final HtmlHandler<Map<String, Object>> includeHtmlHandler;

    private int start;

    private boolean next;

    private String cardUrl;

    public OurocgDataFinder(HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler,
                            HtmlHandler<Map<String, Object>> includeHtmlHandler) {
        this.start = 1;
        this.next = true;
        this.cardUrl = "https://www.ourocg.cn/card/list-5/";
        this.cardInfoHtmlHandler = cardInfoHtmlHandler;
        this.includeHtmlHandler = includeHtmlHandler;
    }

    @Override
    public Iterator<List<MetaData>> obtainCards() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.next;
    }

    @Override
    public List<MetaData> next() {
        try {
            List<Map<String, String>> infos = cardInfoHtmlHandler.handle(this.cardUrl + start);
            List<MetaData> includes = infos.stream().map(data -> {
                Map<String, Object> map = includeHtmlHandler.handle(data.get("href"));
                if (data.get("password").equals(map.get("password"))) {
                    data.put("adjust", map.get("adjust") == null ? map.get("adjust").toString() : "");
                }

                return OurocgMetaDataAdapter.changeInclude2MetaData(map.get("password").toString(), map.get("includeInfos"));
            }).filter(data -> !data.getData().equals("[]")).collect(Collectors.toList());
            List<MetaData> result = OurocgMetaDataAdapter.changeCard2MetaData(infos);
            this.start = start + 1;
            result.addAll(includes);
            return result;
        } catch (Exception e) {
            this.start = start + 1;
            return Lists.newArrayList();
        }
    }

    @Override
    public CardDTO data2CardDTO(String cardData) {
        CardDTO cardDTO = new CardDTO();
        Map<String, String> map = JsonParser.defaultInstance().readObjectValue(cardData, Map.class);
        cardDTO.setPassword(getMapValue(map,"password"));
        cardDTO.setName(getMapValue(map,"name"));
        cardDTO.setNameJa(getMapValue(map,"name_ja"));
        cardDTO.setNameEn(getMapValue(map,"name_en"));
        cardDTO.setNameNw(getMapValue(map,"name_nw"));
        cardDTO.setLevel(getMapValue(map,"level"));
        cardDTO.setRace(getMapValue(map,"race"));
        cardDTO.setAttribute(getMapValue(map,"attribute"));
        cardDTO.setAtk(getMapValue(map,"atk"));
        cardDTO.setDef(getMapValue(map,"def"));
        cardDTO.setPend(getMapValue(map,"pend_l"));
        cardDTO.setLink(getMapValue(map,"link"));
        cardDTO.setDesc(effectFormat(getMapValue(map,"desc")));
        cardDTO.setDescJa(effectFormat(getMapValue(map,"desc_ja")));
        cardDTO.setDescEn(effectFormat(getMapValue(map,"desc_en")));
        cardDTO.setDescNw(effectFormat(getMapValue(map,"desc_nw")));
        cardDTO.setImgUrl(getMapValue(map,"img_url"));
        if ("1".equals(getMapValue(map,"type_val"))) {
            cardDTO.setTypeVal("怪兽");
        }

        if ("2".equals(getMapValue(map,"type_val"))) {
            cardDTO.setTypeVal("魔法");
        }

        if ("3".equals(getMapValue(map,"type_val"))) {
            cardDTO.setTypeVal("陷阱");
        }

        cardDTO.setAdjust(getMapValue(map,"adjust"));
        String linkArrow = getMapValue(map,"link_arrow");
        if ((linkArrow.contains(",") || linkArrow.contains("，"))) {
            linkArrow = linkArrow.replace("，", ",");
            cardDTO.setLinkArrow(Lists.newArrayList(linkArrow.split(",")));
        }

        String typeSt = getMapValue(map,"type_st");
        if (typeSt.contains("|")) {
            typeSt = typeSt.replace("怪兽|", "")
                    .replace("魔法|", "")
                    .replace("陷阱|", "");
            cardDTO.setTypeSt(Lists.newArrayList(typeSt.split("\\|")));
        }

        return cardDTO;
    }

    private String getMapValue(Map<String, String> map, String key) {
        String value = map.get(key);
        return (value == null || "null".equals(value)) ? "" : value;
    }

    private String effectFormat(String str) {
        if (str == null) return "";
        while (true) {
            int index = str.indexOf("@#");
            if (!str.contains("@#")) {
                break;
            }

            int nextIndex = str.indexOf("@", index + 3);
            String source = str.substring(index, nextIndex + 1);
            String target = str.substring(index + 2, nextIndex);
            str = str.replace(source, target);
        }

        return str;
    }

    @Override
    public List<IncludeDTO> data2IncludeDTO(String cardData) {
        List<Map<String, String>> map = JsonParser.defaultInstance().parseJsonArray2Map(cardData);
        return map.stream().map(data -> new IncludeDTO(
                data.get("packName"),
                data.get("sellTime"),
                data.get("number"),
                data.get("rare"),
                data.get("shortName"),
                data.get("password")
        )).collect(Collectors.toList());
    }
}
