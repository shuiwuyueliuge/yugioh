package cn.mayu.yugioh.gateway.route.application;

import cn.mayu.yugioh.common.web.expection.BaseException;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/7/15 4:10 下午
 */
public class ServiceInstanceNotFoundException extends BaseException {

    public ServiceInstanceNotFoundException(int code, String restMsg) {
        super(code, restMsg);
    }
}
