package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.domain.aggregate.*;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurocgCardAdapter {

    public static CardDetail getCardDTOList(List<Map<String, String>> mapList) {
        CardDetail cardDetail = new CardDetail();
        mapList.stream().forEach(map -> {
            CardIdentity cardIdentity = new CardIdentity(map.get("password"));
            Name name = new Name(map.get("name"), map.get("name_ja"), map.get("name_en"), map.get("name_nw"));
            List<String> linkRows = Lists.newArrayList();
            String linkArrow = map.get("link_arrow");
            if ("null".equals(linkArrow) && (linkArrow.indexOf(",") != -1 || linkArrow.indexOf("，") != -1)) {
                linkArrow = linkArrow.replace("，", ",");
                linkRows = Lists.newArrayList(linkArrow.split(","));
            }

            Monster monster = new Monster(
                    map.get("level"),
                    map.get("attribute"),
                    map.get("race"),
                    map.get("atk"),
                    map.get("def"),
                    map.get("pend_l"),
                    map.get("link"),
                    linkRows
            );


            Description description = new Description(
                    effectFormat(map.get("desc")),
                    effectFormat(map.get("desc_ja")),
                    effectFormat(map.get("desc_en")),
                    effectFormat(map.get("desc_nw"))
            );

            List<String> typeSts = Lists.newArrayList();
            String typeSt = map.get("type_st");
            if ("null".equals(typeSt) && typeSt.indexOf("\\|") != -1) {
                typeSts = Lists.newArrayList(typeSt.split("\\|"));
            }

            Card card = new Card(
                    cardIdentity,
                    name,
                    map.get("img_url"),
                    monster,
                    description,
                    typeSts,
                    null,
                    map.get("type_val"),
                    null
            );

            cardDetail.setCards(card);
            cardDetail.setDetailUrl(map.get("href"));
        });

        return cardDetail;
    }

    public static String effectFormat(String str) {
        if (str == null) {
            return str;
        }

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

    public static List<Include> getIncludeInfo(IncludeDetail include) {
        List<IncludeDetail.IncludeInfo> includeInfos = include.getIncludeInfos();
        return includeInfos.stream().map(data ->
                new Include(data.getPackName(),
                        data.getSellTime(),
                        data.getNumber(),
                        data.getRare(),
                        data.getShortName())
        ).collect(Collectors.toList());
    }
}

@Getter
class CardDetail {

    private List<Card> cards;

    private List<String> detailUrl;

    public CardDetail() {
        this.cards = Lists.newArrayList();
        this.detailUrl = Lists.newArrayList();
    }

    public void setCards(Card card) {
        this.cards.add(card);
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl.add(detailUrl);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class IncludeDetail {

    private List<IncludeInfo> includeInfos;

    private String adjust;

    private String cardName;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IncludeInfo {

        private String rare;

        private String packName;

        private String number;

        private String shortName;

        private String sellTime;

        private String href;
    }
}
