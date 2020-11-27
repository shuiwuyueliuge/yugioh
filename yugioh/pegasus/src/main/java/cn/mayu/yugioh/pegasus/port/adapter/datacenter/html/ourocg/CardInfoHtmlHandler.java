package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.ourocg;

import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.pegasus.application.CardDTO;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.DefaultHtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlParser;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HttpStatusCodeInterceptorChain;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardInfoHtmlHandler extends DefaultHtmlHandler<List<CardDTO>> {

    private static final String CARD_DATA_TAG = "script";

    private static final int CARD_DATA_TAG_INDEX = 2;

    private static final String REPLACE_SOURCE = "package";

    private static final String REPLACE_TARGET = "packages";

    private static final String SUB_START = "{";

    private static final String SUB_END = "}";

    @Override
    protected List<CardDTO> htmlTranslate(HtmlParser parser) {
        String data = parser.parseByTagIndex(CARD_DATA_TAG, CARD_DATA_TAG_INDEX).toString();
        String str = cardDataFilter(data);
        List<Map<String, String>> mapList = JsonParser.builder().failOnUnknownProperties(false).build()
                .findArrayByKey(str, "cards").parseJsonArray2Map();
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
            card.setDesc(map.get("desc"));
            card.setBan(map.get("locale"));
            card.setDescEn(map.get("desc_en"));
            card.setDescJa(map.get("desc_ja"));
            card.setDescNw(map.get("desc_nw"));
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

            String rare = map.get("rare");
            if ("null".equals(rare) && (rare.indexOf(",") != -1 || rare.indexOf("，") != -1)) {
                rare = rare.replace("，", ",");
                card.setRare(Lists.newArrayList(rare.split(",")));
            }

            card.setCardUrl(map.get("href"));
            return card;
        }).collect(Collectors.toList());
    }


    private String cardDataFilter(String metaData) {
        return metaData.substring(metaData.indexOf(SUB_START), metaData.lastIndexOf(SUB_END) + 1)
                .replace(REPLACE_SOURCE, REPLACE_TARGET);
    }

    @Override
    protected void addHttpStatusCodeInterceptor(HttpStatusCodeInterceptorChain interceptorChain) {
        interceptorChain.addInterceptor(new RetryStatusCodeInterceptor());
    }
}
