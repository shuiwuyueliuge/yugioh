package com.mayu.yugioh.common.web.reactive.handler;

import cn.mayu.yugioh.common.basic.tool.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestResult {

    private int code;

    private Object data;

    private String msg;

    public RestResult(int code, String msg) {
        this(code, null, msg);
    }

    public RestResult(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static byte[] toBytes(RestResult result) {
        return JsonCreator.defaultInstance().writeValueAsBytes(result);
    }
}
