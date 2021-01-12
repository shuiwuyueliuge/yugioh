package cn.mayu.yugioh.common.web.result;

public interface RestCodeGenerator {

    int successCode();

    int exceptionCode();

    String exceptionMsg();
}
