package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.ourocg;

import cn.mayu.yugioh.pegasus.domain.aggregate.cardlist.CardInfo;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.DefaultHtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;

public class CardInfoHtmlHandler extends DefaultHtmlHandler<List<CardInfo>> {

    private static final String CARD_DATA_TAG = "script";

    private static final int CARD_DATA_TAG_INDEX = 2;

    private static final String REPLACE_SOURCE = "package";

    private static final String REPLACE_TARGET = "packages";

    private static final String SUB_START = "{";

    private static final String SUB_END = "}";

    @Override
    protected List<CardInfo> htmlTranslate(HtmlParser parser) {
        String data = parser.parseByTagIndex(CARD_DATA_TAG, CARD_DATA_TAG_INDEX).toString();
        String str = cardDataFilter(data);
        System.out.println(str);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(str);
            jsonNode.
        } catch (JsonProcessingException e) {
        }

        return Lists.newArrayList();
    }

    private String cardDataFilter(String metaData) {
        return metaData.substring(metaData.indexOf(SUB_START), metaData.lastIndexOf(SUB_END) + 1)
                .replace(REPLACE_SOURCE, REPLACE_TARGET);
    }
}
