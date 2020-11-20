package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html;

public abstract class HttpStatusCodeInterceptor {

	protected void handleStatusCode(String url, HtmlParser htmlParser) {
		if (isHandle(htmlParser)) {
			handle(url, htmlParser);
		}
	}

	public abstract boolean isHandle(HtmlParser htmlParser);

	public abstract void handle(String url, HtmlParser htmlParser);
}
