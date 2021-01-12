package cn.mayu.yugioh.common.web.result;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface ResultWrapper {

    String RESPONSE_BODY_ANN = ResultWrapper.class.getName();
}