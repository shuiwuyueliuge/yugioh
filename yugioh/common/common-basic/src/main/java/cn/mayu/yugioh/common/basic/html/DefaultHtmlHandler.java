package cn.mayu.yugioh.common.basic.html;

/**
 * HtmlHandler的模板类
 * 对url访问并返回指定的对象，
 * 对指定的http response状态码进行拦截
 * @param <T>
 */
public abstract class DefaultHtmlHandler<T> implements HtmlHandler<T> {

	@Override
	public T handle(String url) throws HtmlHandlerException {
		HtmlParser parser = new HtmlParser(url);
		HttpStatusCodeInterceptorChain interceptorChain = new HttpStatusCodeInterceptorChain();
		addHttpStatusCodeInterceptor(interceptorChain);
		interceptorChain.handleStatusCode(url, parser);
		return htmlTranslate(parser);
	}
	
	protected abstract T htmlTranslate(HtmlParser parser);

	/**
	 * 添加http response状态码的拦截器
	 * example: interceptorChain.addInterceptor(new RetryStatusCodeInterceptor())
	 * 							.addInterceptor(new NotFoundStatusCodeInterceptor());
	 */
	protected void addHttpStatusCodeInterceptor(HttpStatusCodeInterceptorChain interceptorChain) {}
}
