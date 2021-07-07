package com.mayu.yugioh.common.rpc.test;

import feign.Feign;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestApp.class)
@Slf4j
@Data
class FeignTests {

    @Test
    void contextLoads() {
        TestFacade testFacade = Feign.builder().target(TestFacade.class, "http://www.baidu.com");
        log.info(testFacade.baiduApiTest());
    }
}
