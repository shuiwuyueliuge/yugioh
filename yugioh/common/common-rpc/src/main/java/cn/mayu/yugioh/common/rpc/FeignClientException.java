package cn.mayu.yugioh.common.rpc;

import feign.FeignException;
import feign.Request;
import lombok.Getter;

/**
 * @description: 扩展FeignException
 * @author: YgoPlayer
 * @time: 2021/6/2 4:18 下午
 */
@Getter
public class FeignClientException extends FeignException {

    protected FeignClientException(int code, String msg, Request request) {
        super(code, msg, request);
    }
}