package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.application.datacenter.*;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OurocgDataCenter extends AbstractDataCenterFactory {

    private HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private HtmlHandler<Map<String, Object>> includeHtmlHandler;

    public OurocgDataCenter() {
        this.cardInfoHtmlHandler = new CardInfoHtmlHandler();
        this.includeHtmlHandler = new IncludeInfoHandler();
    }

    @Override
    public CardData getCardData() {
        return new OurocgCardDataCenter(cardInfoHtmlHandler, includeHtmlHandler);
    }

    @Override
    public IncludeData getIncludeData() {
        return new OurocgIncludeDataCenter();
    }

    @Override
    public DataCenterEnum type() {
        return DataCenterEnum.OUROCG;
    }
}
