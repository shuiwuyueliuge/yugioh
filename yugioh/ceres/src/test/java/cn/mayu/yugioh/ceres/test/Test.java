package cn.mayu.yugioh.ceres.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestApp.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class LogTest {

    @Test
    void contextLoads() {
        log.info("info test");
    }
}