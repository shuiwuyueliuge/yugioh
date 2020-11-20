package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html;

import com.google.common.collect.Lists;
import java.util.List;

public class HttpStatusCodeInterceptorChain {
	
	private List<HttpStatusCodeInterceptor> chain;
	
	{
		chain = Lists.newArrayList();
	}

	protected void handleStatusCode(String url, HtmlParser htmlParser) {
		chain.stream().forEach(interceptor -> interceptor.handleStatusCode(url, htmlParser));
	}
	
	public HttpStatusCodeInterceptorChain addInterceptor(HttpStatusCodeInterceptor interceptor) {
		chain.add(interceptor);
		return this;
	}
}
