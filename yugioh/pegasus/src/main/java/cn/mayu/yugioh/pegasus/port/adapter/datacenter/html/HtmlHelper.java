package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class HtmlHelper {
	
	private static final String AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)  Chrome/56.0.2924.87 Safari/537.36";

	private static final int TIMEOUT_MILLIS = 50000;
	
	protected static VisitResponse connect(String url) {
		try {
			Response res = getResponse(url);
			return new VisitResponse(res.statusCode(), res.headers(), res.body());
		} catch (Exception e) {
			log.error("connect [{}] error [{}]", url, e);
			return new VisitResponse(0, Collections.emptyMap(), "");
		}
	}
	
	protected static String getElementsByTagIndex(String html, String tag, int index) {
		Elements els = parse(html).getElementsByTag(tag);
		return els.size() <= index ? "" : els.get(index).html();
	}

	protected static String getElementsByClassIndex(String html, String className, int index) {
		Elements els = parse(html).getElementsByClass(className);
		return els.size() <= index ? "" : els.get(index).html();
	}
	
	private static Response getResponse(String url) throws Exception {
		return Jsoup.connect(url)
				    .ignoreContentType(false)
				    .timeout(TIMEOUT_MILLIS)
				    .userAgent(AGENT)
				    .ignoreHttpErrors(true)
				    .execute();
	}
	
	private static Document parse(String html) {
		return Jsoup.parse(html);
	}

	protected static String[] getElementsByTag(String html, String tagName) {
		Elements els = parse(html).getElementsByTag(tagName);
		String res[] = new String[els.size()];
		int len = els.size();
		for (int i = 0; i < len; i++) {
			res[i] = els.get(i).html();
		}
		
		return res;
	}
	
	protected static String[] getElementsByTagAttr(String html, String tagName, String attr) {
		Elements els = parse(html).getElementsByTag(tagName);
		int len = els.size();
		String res[] = new String[len];
		for (int i = 0; i < len; i++) {
			res[i] = els.get(i).attr(attr);
		}
		
		return res;
	}

	protected static List<Map<String, String>> getTextAndAttrs(String html, String tagName, String textKey) {
		Elements els = parse(html).getElementsByTag(tagName);
		int len = els.size();
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			Attributes attributes = els.get(i).attributes();
			List<Attribute> attributeList = attributes.asList();
			Map<String, String> map = attributeList.stream().collect(Collectors.toMap(data -> data.getKey(), data -> data.getValue()));
			map.put(textKey, els.get(i).html());
			list.add(map);
		}

		return list;
	}

	protected static String getElementsByTagIndexAttr(String html, String tagName, int index, String attribute) {
		Elements els = parse(html).getElementsByTag(tagName);
		return els.size() <= index ? "" : els.get(index).attr(attribute);
	}
}
