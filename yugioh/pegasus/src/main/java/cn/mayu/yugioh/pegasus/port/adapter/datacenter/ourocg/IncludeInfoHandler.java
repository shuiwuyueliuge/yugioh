package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.html.DefaultHtmlHandler;
import cn.mayu.yugioh.common.basic.html.HtmlParser;
import cn.mayu.yugioh.common.basic.html.HttpStatusCodeInterceptorChain;
import cn.mayu.yugioh.common.basic.tool.HashGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IncludeInfoHandler extends DefaultHtmlHandler<Map<String, Object>> {

    @Override
    protected Map<String, Object> htmlTranslate(HtmlParser parser) {
        String[] template = parser.parseByTag("template");
        String[] div = parser.parseByTag("div");
        String password = "";
        for (int i = 0; i < div.length; i++) {
            if ("卡片密码".equals(div[i])) {
                password = div[i + 1];
                break;
            }
        }

        String html = parser.toString();
        String[] res = parser.parseByTag("td");
        List<Map<String, String>> infos = Lists.newArrayList();
        collectToList(infos, res, parser);
        String adjust = parseAdjust(parser.setHtml(html));
        Map<String, Object> cd = Maps.newHashMap();
        cd.put("adjust", adjust);
        cd.put("includeInfos", infos);
        cd.put("password", StringUtils.isEmpty(password) ? HashGenerator.createHashStr(template[0]) : password);
        return cd;
    }

    private void collectToList(List<Map<String, String>> infos, String[] res, HtmlParser parser) {
        for (int i = 3; i <= res.length; i += 3) {
            Map<String, String> builder = Maps.newHashMap();
            String number = res[i - 2];
            if (number.indexOf("-") != -1) {
                builder.put("shortName", number.substring(0, number.indexOf("-")));
            }

            builder.put("packName", parser.setHtml(res[i - 3]).parseByTagIndex("a", 0).toString());
            builder.put("href", parser.setHtml(res[i - 3]).parseByTagAttr("a", "href")[0]);
            builder.put("sellTime", parser.setHtml(res[i - 3]).parseByTagIndex("small", 0).toString());
            builder.put("number", number.equals("&nbsp;") ? "" : number);
            builder.put("rare", res[i - 1]);
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
