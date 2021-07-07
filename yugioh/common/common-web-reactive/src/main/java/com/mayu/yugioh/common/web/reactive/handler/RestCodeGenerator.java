package com.mayu.yugioh.common.web.reactive.handler;

public interface RestCodeGenerator {

    default int restSuccessCode() {
        return 200;
    }

    default String restSuccessMsg() {
        return "SUCCESS";
    }

    default int restSystemErrorCode() {
        return 500;
    }

    default String restSystemErrorMsg() {
        return "SYSTEM_ERROR";
    }
}
