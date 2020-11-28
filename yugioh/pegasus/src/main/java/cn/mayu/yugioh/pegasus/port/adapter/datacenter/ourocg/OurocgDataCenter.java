package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.application.CardDTO;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.AbstractDataCenter;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlHandlerException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OurocgDataCenter extends AbstractDataCenter {

    private HtmlHandler<List<Map<String, String>>> cardInfoHtmlHandler;

    private HtmlHandler<Include> includeHtmlHandler;

    private String adjust;

    public OurocgDataCenter() {
        this.cardInfoHtmlHandler = new CardInfoHtmlHandler();
        this.includeHtmlHandler = new IncludeInfoHandler();
    }

    @Override
    public DataCenterEnum type() {
        return DataCenterEnum.OUROCG;
    }

    @Override
    public Iterator<List<CardDTO>> obtainCards() {
        return new OurocgDataCardIterator(cardInfoHtmlHandler);
    }

    @Override
    public List<CardDTO.IncludeInfo> obtainIncluded(String resources) {
        try {
            Include include = includeHtmlHandler.handle(resources);
            adjust = include.getAdjust();
            return OurocgAdapter.getIncludeInfo(include);
        } catch (HtmlHandlerException e) {
            log.error("url:[{}] status code:[{}]", e.getUrl(), e.getStatusCode());
            return Lists.newArrayList();
        }
    }

    @Override
    public String adjust(String resources) {
        String result = adjust;
        adjust = "";
        return result;
    }
}
