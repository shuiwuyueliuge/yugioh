package cn.mayu.yugioh.common.basic.html;

/**
 * @description: html拦截错误
 * @author: YgoPlayer
 * @time: 2021/5/17 9:50 上午
 */
public class HtmlHandlerException extends Exception {

    public HtmlHandlerException(String url, int statusCode) {
        super(String.format("connect %s status %s", url, statusCode));
    }
}
