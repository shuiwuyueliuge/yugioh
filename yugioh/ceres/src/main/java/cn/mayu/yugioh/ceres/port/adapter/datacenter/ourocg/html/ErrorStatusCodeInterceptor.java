package cn.mayu.yugioh.ceres.port.adapter.datacenter.ourocg.html;

import cn.mayu.yugioh.common.basic.html.HtmlHandlerException;
import cn.mayu.yugioh.common.basic.html.HtmlParser;
import cn.mayu.yugioh.common.basic.html.HttpStatusCodeInterceptor;
import cn.mayu.yugioh.common.basic.html.VisitResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorStatusCodeInterceptor extends HttpStatusCodeInterceptor {

    @Override
    public boolean isHandle(HtmlParser htmlParser) {
        VisitResponse response = htmlParser.getResponse();
        int statusCode = response.getStatusCode();
        return statusCode == 500;
    }

    @Override
    public void handle(String url, HtmlParser htmlParser) throws HtmlHandlerException {
        throw new HtmlHandlerException(url, 500);
    }
}
