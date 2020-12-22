package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.application.datacenter.*;
import cn.mayu.yugioh.common.basic.html.HtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg.html.CardInfoHtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg.html.IncludeInfoHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OurocgDataCenter extends AbstractDataCenterFactory {

    private final HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private final HtmlHandler<Map<String, Object>> includeHtmlHandler;

    public OurocgDataCenter() {
        this.cardInfoHtmlHandler = new CardInfoHtmlHandler();
        this.includeHtmlHandler = new IncludeInfoHandler();
    }

    @Override
    public CardData getCardData() {
        return new OurocgDataFinder(cardInfoHtmlHandler, includeHtmlHandler);
    }

    @Override
    public IncludeData getIncludeData() {
        return new OurocgDataFinder(cardInfoHtmlHandler, includeHtmlHandler);
    }

    @Override
    public DataCenterEnum type() {
        return DataCenterEnum.OUROCG;
    }
}
