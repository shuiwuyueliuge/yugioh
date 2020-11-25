package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.ourocg;

import cn.mayu.yugioh.common.basic.json.JsonParser;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardinfo.CardInfo;
import cn.mayu.yugioh.pegasus.domain.aggregate.cardinfo.CardInfoIdentity;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.DefaultHtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlParser;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HttpStatusCodeInterceptorChain;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return JsonParser.builder().failOnUnknownProperties(false).build()
                .findArrayByKey(str, "cards").parseJsonArray2Map().stream()
                .map(this::map2CardInfo).collect(Collectors.toList());
    }

    private CardInfo map2CardInfo(Map<String, String> map) {
        String imgUrl = map.get("img_url");
        String name = map.get("name");
        String href = map.get("href");
        CardInfoIdentity cardInfoIdentity = new CardInfoIdentity("ourocg", name);
        return new CardInfo(cardInfoIdentity, imgUrl, name, href);
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
