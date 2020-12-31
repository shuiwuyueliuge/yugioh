package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.tool.HashGenerator;
import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.pegasus.application.DataCenterCommandService;
import cn.mayu.yugioh.pegasus.application.command.IncludeInfoCreateCommand;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.IncludeData;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.CardData;
import cn.mayu.yugioh.common.basic.html.HtmlHandler;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgDataFinder implements CardData, IncludeData, Iterator<List<CardDTO>> {

    private final HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private final HtmlHandler<Map<String, Object>> includeHtmlHandler;

    private final DataCenterCommandService dataCenterCommandService;

    private int start;

    private boolean next;

    private String cardUrl;

    private String adjust;

    public OurocgDataFinder(HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler,
                            HtmlHandler<Map<String, Object>> includeHtmlHandler,
                            DataCenterCommandService dataCenterCommandService) {
        this.start = 1105;
        this.next = true;
        this.cardUrl = "https://www.ourocg.cn/card/list-5/";
        this.cardInfoHtmlHandler = cardInfoHtmlHandler;
        this.includeHtmlHandler = includeHtmlHandler;
        this.dataCenterCommandService = dataCenterCommandService;
    }

    @Override
    public Iterator<List<CardDTO>> obtainCards() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.next;
    }

    @Override
    public List<CardDTO> next() {
        try {
            List<Map<String, String>> infos = cardInfoHtmlHandler.handle(this.cardUrl + start);
            infos.stream().forEach(data ->
                    dataCenterCommandService.createIncludeInfo(
                            new IncludeInfoCreateCommand(DataCenterEnum.OUROCG, "", data.get("password"), data.get("href"))));
            List<CardDTO> result = infos.stream().map(data -> {
                data.put("password", data.get("password").replace("null", ""));
                if (StringUtils.isEmpty(data.get("password"))) {
                    data.put("password", HashGenerator.createHashStr(data.get("name")));
                }

                return data2CardDTO(JsonConstructor.defaultInstance().writeValueAsString(data));
            }).collect(Collectors.toList());
            if (result.size() < 10) {
                this.next = false;
                return result;
            }

            this.start = start + 1;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //throw e;
            this.start = start + 1;
            return Lists.newArrayList();
        }
    }

    public CardDTO data2CardDTO(String cardData) {
        CardDTO cardDTO = new CardDTO();
        Map<String, String> map = JsonParser.defaultInstance().readObjectValue(cardData, Map.class);
        cardDTO.setPassword(getMapValue(map, "password"));
        cardDTO.setName(getMapValue(map, "name"));
        cardDTO.setNameJa(getMapValue(map, "name_ja"));
        cardDTO.setNameEn(getMapValue(map, "name_en"));
        cardDTO.setNameNw(getMapValue(map, "name_nw"));
        cardDTO.setLevel(getMapValue(map, "level"));
        cardDTO.setRace(getMapValue(map, "race"));
        cardDTO.setAttribute(getMapValue(map, "attribute"));
        cardDTO.setAtk(getMapValue(map, "atk"));
        cardDTO.setDef(getMapValue(map, "def"));
        cardDTO.setPend(getMapValue(map, "pend_l"));
        cardDTO.setLink(getMapValue(map, "link"));
        cardDTO.setDesc(effectFormat(getMapValue(map, "desc")));
        cardDTO.setDescJa(effectFormat(getMapValue(map, "desc_ja")));
        cardDTO.setDescEn(effectFormat(getMapValue(map, "desc_en")));
        cardDTO.setDescNw(effectFormat(getMapValue(map, "desc_nw")));
        cardDTO.setImgUrl(getMapValue(map, "img_url"));
        if ("1".equals(getMapValue(map, "type_val"))) {
            cardDTO.setTypeVal("怪兽");
        }

        if ("2".equals(getMapValue(map, "type_val"))) {
            cardDTO.setTypeVal("魔法");
        }

        if ("3".equals(getMapValue(map, "type_val"))) {
            cardDTO.setTypeVal("陷阱");
        }

        cardDTO.setAdjust(getMapValue(map, "adjust"));
        String linkArrow = getMapValue(map, "link_arrow");
        if ((linkArrow.contains(",") || linkArrow.contains("，"))) {
            linkArrow = linkArrow.replace("，", ",");
            cardDTO.setLinkArrow(Lists.newArrayList(linkArrow.split(",")));
        }

        String typeSt = getMapValue(map, "type_st");
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
    public List<IncludeDTO> obtainIncludes(String password, String source) {
        Map<String, Object> map = includeHtmlHandler.handle(source);
        this.adjust = map.get("adjust") == null ? map.get("adjust").toString() : "";
        String jsonData = JsonConstructor.defaultInstance().writeValueAsString(map.get("includeInfos"));
        List<IncludeDTO> includes = data2IncludeDTO(jsonData);
        return includes.stream().filter(include -> !"[]".equals(include)).collect(Collectors.toList());
    }

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
