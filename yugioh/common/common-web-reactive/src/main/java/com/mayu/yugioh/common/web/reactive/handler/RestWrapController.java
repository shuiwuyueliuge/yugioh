package com.mayu.yugioh.common.web.reactive.handler;

import org.springframework.stereotype.Controller;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface RestWrapController {
}
