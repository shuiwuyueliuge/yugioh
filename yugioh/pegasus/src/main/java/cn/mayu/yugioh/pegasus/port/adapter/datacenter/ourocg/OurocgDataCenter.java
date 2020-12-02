package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.domain.aggregate.Card;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.AbstractDataCenter;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OurocgDataCenter extends AbstractDataCenter {

    private HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private HtmlHandler<IncludeDetail> includeHtmlHandler;

    public OurocgDataCenter() {
        this.cardInfoHtmlHandler = new CardInfoHtmlHandler();
        this.includeHtmlHandler = new IncludeInfoHandler();
    }

    @Override
    public DataCenterEnum type() {
        return DataCenterEnum.OUROCG;
    }

    @Override
    public Iterator<List<Card>> obtainCards() {
        return new OurocgDataCardIterator(cardInfoHtmlHandler, includeHtmlHandler);
    }
}
