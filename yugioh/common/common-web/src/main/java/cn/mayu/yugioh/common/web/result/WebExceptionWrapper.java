package cn.mayu.yugioh.common.web.result;

import cn.mayu.yugioh.common.web.expection.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionWrapper {

    @Autowired
    private RestCodeGenerator restCodeGenerator;

    @ExceptionHandler(BaseException.class)
    public BaseException exception(BaseException e) {
        return e;
    }

    @ExceptionHandler(Exception.class)
    public BaseException exception(Exception e) {
        return new BaseException(restCodeGenerator.exceptionCode(), restCodeGenerator.exceptionMsg(), e);
    }
}