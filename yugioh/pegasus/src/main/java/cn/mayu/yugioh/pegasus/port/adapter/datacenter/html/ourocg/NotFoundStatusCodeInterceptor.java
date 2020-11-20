package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.ourocg;

import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HtmlParser;
import cn.mayu.yugioh.pegasus.port.adapter.datacenter.html.HttpStatusCodeInterceptor;

public class NotFoundStatusCodeInterceptor extends HttpStatusCodeInterceptor {

	@Override
	public boolean isHandle(HtmlParser htmlParser) {
		return htmlParser.getStateCode() == 404;
	}

	@Override
	public void handle(String url, HtmlParser htmlParser) {
		throw new RuntimeException(url + " is 404");
	}
}
