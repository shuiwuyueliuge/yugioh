package cn.mayu.yugioh.common.rpc;

import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.common.web.handler.RestCodeGenerator;
import cn.mayu.yugioh.common.web.handler.RestResult;
import com.fasterxml.jackson.databind.type.TypeFactory;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @description: feign的解码器,处理下游的系统异常或者业务异常，返回值为void不经过此解码
 * @author: YgoPlayer
 * @time: 2021/6/2 4:17 下午
 */
@Slf4j
public class FeignDecoder implements Decoder {

    private final int successCode;

    public FeignDecoder(RestCodeGenerator restCodeGenerator) {
        this.successCode = restCodeGenerator.restSuccessCode();
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.status() == 404 || response.status() == 204) {
            return Util.emptyValueOf(type);
        }

        if (Objects.isNull(response.body())) {
            return null;
        }

        JsonParser jsonParser = JsonParser.defaultInstance();
        // 此处之前代码注意不要打断点，会出现IoException: stream is closed
        String resString = Util.toString(response.body().asReader(Util.UTF_8));
        RestResult result;
        try {
            result = jsonParser.readObjectValue(resString, RestResult.class);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("not cn.mayu.yugioh.common.web.handler.RestResult response body");
            }

            return resString;
        }

        if (log.isDebugEnabled()) {
            log.debug("Feign({}) Response body: [{}]", response.request().url(), result);
        }

        if (Objects.isNull(result)) {
            return null;
        }

        // 处理下游异常
        if (result.getCode() != successCode) {
            throw new FeignClientException(result.getCode(), result.getMsg(), response.request());
        }

        // 返回结果
        Object data = result.getData();
        return jsonParser.convertValue(data, TypeFactory.defaultInstance().constructType(type));
    }
}
