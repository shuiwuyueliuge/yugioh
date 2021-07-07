package com.mayu.yugioh.common.web.reactive.handler;

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
}
