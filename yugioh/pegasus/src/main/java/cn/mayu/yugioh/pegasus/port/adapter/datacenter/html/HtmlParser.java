package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html;

import java.util.List;
import java.util.Map;

import static cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlHelper.*;

public class HtmlParser {

    private String html;

    private VisitResponse response;

    private String url;

    protected HtmlParser(String url) {
        this.url = url;
        connUrl();
    }

    public HtmlParser connUrl() {
        this.response = connect(url);
        this.html = getResponse().getHtml();
        return this;
    }

    public HtmlParser parseByTagIndex(String tagName, int index) {
        this.html = getElementsByTagIndex(html, tagName, index);
        return this;
    }

    public HtmlParser parseByTagIndexAttr(String tagName, int index, String attribute) {
        this.html = getElementsByTagIndexAttr(html, tagName, index, attribute);
        return this;
    }

    public String[] parseByTag(String tagName) {
        return getElementsByTag(html, tagName);
    }

    public String[] parseByTagAttr(String tagName, String attr) {
        return getElementsByTagAttr(html, tagName, attr);
    }

    public List<Map<String, String>> parseTextAndAttrs(String tagName, String textKey) {
        return getTextAndAttrs(html, tagName, textKey);
    }

    public HtmlParser parseByClassIndex(String className, int index) {
        this.html = getElementsByClassIndex(html, className, index);
        return this;
    }

    @Override
    public String toString() {
        return html;
    }

    public HtmlParser setHtml(String html) {
        this.html = html;
        return this;
    }

    public VisitResponse getResponse() {
        return response;
    }

    public int getStateCode() {
        return response.getStatusCode();
    }

    public String getUrl() {
        return url;
    }
}
