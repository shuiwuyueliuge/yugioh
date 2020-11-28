package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.DefaultHtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlParser;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HttpStatusCodeInterceptorChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import static cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg.Include.IncludeInfo;
import java.util.List;

@Slf4j
@Component
public class IncludeInfoHandler extends DefaultHtmlHandler<Include> {

    @Override
    protected Include htmlTranslate(HtmlParser parser) {
        String[] s = parser.parseByTag("template");
        String html = parser.toString();
        String[] res = parser.parseByTag("td");
        List<IncludeInfo> infos = new ArrayList<IncludeInfo>();
        collectToList(infos, res, parser);
        String adjust = parseAdjust(parser.setHtml(html));
        Include cd = new Include();
        cd.setAdjust(adjust);
        cd.setIncludeInfos(infos);
        cd.setCardName(s[0]);
        return cd;
    }

    private void collectToList(List<IncludeInfo> infos, String[] res, HtmlParser parser) {
        for (int i = 3; i <= res.length; i += 3) {
            IncludeInfo builder = new IncludeInfo();
            String number = res[i - 2];
            if (number.indexOf("-") != -1) {
                builder.setShortName(number.substring(0, number.indexOf("-")));
            }

            builder.setPackName(parser.setHtml(res[i - 3]).parseByTagIndex("a", 0).toString());
            builder.setHref(parser.setHtml(res[i - 3]).parseByTagAttr("a", "href")[0]);
            builder.setSellTime(parser.setHtml(res[i - 3]).parseByTagIndex("small", 0).toString());
            builder.setNumber(number.equals("&nbsp;") ? "" : number);
            builder.setRare(res[i - 1]);
            infos.add(builder);
        }
    }

    private String parseAdjust(HtmlParser parser) {
        try {
            String adjust = parser.parseByClassIndex("wiki", 0).parseByTagIndex("li", 1).toString();
            return adjust.equals("　　　　") ? "" : adjust;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    protected void addHttpStatusCodeInterceptor(HttpStatusCodeInterceptorChain interceptorChain) {
        interceptorChain.addInterceptor(new RetryStatusCodeInterceptor())
                        .addInterceptor(new ErrorStatusCodeInterceptor());
    }
}
