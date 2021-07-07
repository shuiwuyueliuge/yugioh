package com.mayu.yugioh.common.web.reactive.expection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseException extends RuntimeException {

    private int code;

    private String restMsg;

    public BaseException(String restMsg) {
        this(0, restMsg);
    }

    public BaseException(int code, String restMsg) {
        super(restMsg);
        this.code = code;
        this.restMsg = restMsg;
    }
}
