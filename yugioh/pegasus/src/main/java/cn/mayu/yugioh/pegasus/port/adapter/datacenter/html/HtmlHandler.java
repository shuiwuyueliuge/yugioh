package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html;

/**
 * 对指定url返回的页面进行处理返回指定的对象
 * @param <T> 指定的对象
 */
public interface HtmlHandler<T> {

	T handle(String url) throws HtmlHandlerException;
}
