package com.mayu.yugioh.common.web.reactive.handler;

public class DefaultRestCodeGenerator implements RestCodeGenerator {

    public int restSuccessCode() {
        return 200;
    }

    public String restSuccessMsg() {
        return "SUCCESS";
    }

    public int restSystemErrorCode() {
        return 500;
    }

    public String restSystemErrorMsg() {
        return "SYSTEM_ERROR";
    }
}
