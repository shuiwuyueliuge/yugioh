package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg.html;

import cn.mayu.yugioh.common.basic.html.HtmlParser;
import cn.mayu.yugioh.common.basic.html.HttpStatusCodeInterceptor;
import cn.mayu.yugioh.common.basic.html.VisitResponse;
import lombok.extern.slf4j.Slf4j;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class RetryStatusCodeInterceptor extends HttpStatusCodeInterceptor {
	
	private static final String RETRY_AFTER = "Retry-After";

	@Override
	public boolean isHandle(HtmlParser htmlParser) {
		return (htmlParser.getStateCode() == 429 &&
				htmlParser.getResponse().getHeaders().get(RETRY_AFTER) != null) ? true : false;
	}

	@Override
	public void handle(String url, HtmlParser parser) {
		VisitResponse response = parser.getResponse();
		int statusCode = response.getStatusCode();
		String retryAfter = response.getHeaders().get(RETRY_AFTER);
		sleep(statusCode, url, retryAfter);
		parser.connUrl();
	}

	private void sleep(int statusCode, String url, String retryAfter) {
		long time = Long.parseLong(retryAfter);
		//if (log.isDebugEnabled()) {
			log.info("connect [{}] status code is [{}] and retry-after is [{}]",
					url, statusCode, retryAfter);
		//}

		try {
			SECONDS.sleep(time + 5);
		} catch (InterruptedException e) {
		}
	}
}
