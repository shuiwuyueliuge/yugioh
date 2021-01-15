package cn.mayu.yugioh.common.web.result;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@ConditionalOnMissingBean(RestCodeGenerator.class)
public class DefaultRestCodeGenerator implements RestCodeGenerator {

    @Override
    public int successCode() {
        return 10000;
    }

    @Override
    public int exceptionCode() {
        return 10001;
    }

    @Override
    public String exceptionMsg() {
        return "system error";
    }
}
