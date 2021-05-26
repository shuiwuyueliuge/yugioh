package cn.mayu.yugioh.common.basic.html;

public abstract class HttpStatusCodeInterceptor {

	protected void handleStatusCode(String url, HtmlParser htmlParser) throws HtmlHandlerException {
		if (isHandle(htmlParser)) {
			handle(url, htmlParser);
		}
	}

	public abstract boolean isHandle(HtmlParser htmlParser);

	public abstract void handle(String url, HtmlParser htmlParser) throws HtmlHandlerException;
}
