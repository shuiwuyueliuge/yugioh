package com.mayu.yugioh.common.rpc.test;//package com.mayu.yugioh.common.web.test.rpc;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public interface TestFacade {

    @RequestLine("GET /")
    String baiduApiTest();
}
