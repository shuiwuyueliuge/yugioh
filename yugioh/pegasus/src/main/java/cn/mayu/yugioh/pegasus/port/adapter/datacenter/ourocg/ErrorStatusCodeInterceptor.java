package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.common.basic.html.HtmlHandlerException;
import cn.mayu.yugioh.common.basic.html.HtmlParser;
import cn.mayu.yugioh.common.basic.html.HttpStatusCodeInterceptor;
import cn.mayu.yugioh.common.basic.html.VisitResponse;

public class ErrorStatusCodeInterceptor extends HttpStatusCodeInterceptor {

    @Override
    public boolean isHandle(HtmlParser htmlParser) {
        VisitResponse response = htmlParser.getResponse();
        int statusCode = response.getStatusCode();
        return statusCode == 500;
    }

    @Override
    public void handle(String url, HtmlParser htmlParser) {
        throw new HtmlHandlerException(500, url);
    }
}
