package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.tool.JsonConstructor;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.common.basic.tool.SnowFlake;
import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaData;
import cn.mayu.yugioh.pegasus.application.datacenter.CardData;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaDataIdentity;
import cn.mayu.yugioh.common.basic.html.HtmlHandler;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgCardDataCenter implements CardData, Iterator<List<MetaData>> {

    private HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private HtmlHandler<Map<String, Object>> includeHtmlHandler;

    private int start;

    private boolean next;

    private String cardUrl;

    public OurocgCardDataCenter(HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler,
                                HtmlHandler<Map<String, Object>> includeHtmlHandler) {
        this.start = 1106;
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
    public CardDTO data2CardDTO(String cardData) {
        CardDTO cardDTO = new CardDTO();
        Map<String, String> map = JsonParser.defaultInstance().readObjectValue(cardData, Map.class);
        cardDTO.setPassword((map.get("password") == null || map.get("password").equals("null")) ? SnowFlake.nextId() + "" : map.get("password"));
        cardDTO.setName(map.get("name"));
        cardDTO.setNameJa(map.get("name_ja"));
        cardDTO.setNameEn(map.get("name_en"));
        cardDTO.setNameNw(map.get("name_nw"));
        cardDTO.setLevel(map.get("level"));
        cardDTO.setRace(map.get("race"));
        cardDTO.setAttribute(map.get("attribute"));
        cardDTO.setAtk(map.get("atk"));
        cardDTO.setDef(map.get("def"));
        cardDTO.setPend(map.get("pend_l"));
        cardDTO.setLink(map.get("link"));
        cardDTO.setDesc(effectFormat(map.get("desc")));
        cardDTO.setDescJa(effectFormat(map.get("desc_ja")));
        cardDTO.setDescEn(effectFormat(map.get("desc_en")));
        cardDTO.setDescNw(effectFormat(map.get("desc_nw")));
        cardDTO.setImgUrl(map.get("img_url"));
        cardDTO.setTypeVal(map.get("type_val"));
        cardDTO.setAdjust(map.get("adjust"));
        String linkArrow = map.get("link_arrow");
        if ("null".equals(linkArrow) && (linkArrow.indexOf(",") != -1 || linkArrow.indexOf("，") != -1)) {
            linkArrow = linkArrow.replace("，", ",");
            cardDTO.setLinkArrow(Lists.newArrayList(linkArrow.split(",")));
        }

        String typeSt = map.get("type_st");
        if ("null".equals(typeSt) && typeSt.indexOf("\\|") != -1) {
            cardDTO.setTypeSt(Lists.newArrayList(typeSt.split("\\|")));
        }

        return cardDTO;
    }

    private String effectFormat(String str) {
        if (str == null) return "";
        while (true) {
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

    @Override
    public boolean hasNext() {
        return this.next;
    }

    @Override
    public List<MetaData> next() {
        List<Map<String, String>> infos = cardInfoHtmlHandler.handle(this.cardUrl + start);
        List<MetaData> includes = infos.stream().map(data -> {
            Map<String, Object> map = includeHtmlHandler.handle(data.get("href"));
            if (data.get("name").equals(map.get("cardName"))) {
                data.put("adjust", map.get("adjust") == null ? map.get("adjust").toString() : "");
            }

            MetaDataIdentity metaDataIdentity = new MetaDataIdentity(map.get("cardName").toString(), DataCenterEnum.OUROCG, "include");
            return new MetaData(metaDataIdentity, JsonConstructor.defaultInstance().writeValueAsString(map.get("includeInfos")));
        }).filter(data -> !data.getData().equals("[]")).collect(Collectors.toList());
        List<MetaData> result = OurocgCardAdapter.getCardDTOList(infos);
        this.start = start + 1;
        result.addAll(includes);
        return result;
    }
}
