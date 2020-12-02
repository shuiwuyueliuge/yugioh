package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.DefaultHtmlHandler;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlParser;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HttpStatusCodeInterceptorChain;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class IncludeInfoHandler extends DefaultHtmlHandler<IncludeDetail> {

    @Override
    protected IncludeDetail htmlTranslate(HtmlParser parser) {
        String[] s = parser.parseByTag("template");
        String html = parser.toString();
        String[] res = parser.parseByTag("td");
        List<IncludeDetail.IncludeInfo> infos = Lists.newArrayList();
        collectToList(infos, res, parser);
        String adjust = parseAdjust(parser.setHtml(html));
        IncludeDetail cd = new IncludeDetail();
        cd.setAdjust(adjust);
        cd.setIncludeInfos(infos);
        cd.setCardName(s[0]);
        return cd;
    }

    private void collectToList(List<IncludeDetail.IncludeInfo> infos, String[] res, HtmlParser parser) {
        for (int i = 3; i <= res.length; i += 3) {
            IncludeDetail.IncludeInfo builder = new IncludeDetail.IncludeInfo();
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
