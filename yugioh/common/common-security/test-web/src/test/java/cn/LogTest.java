package cn;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.test.Test.class)
@Slf4j
class LogTest {

    @Test
    void contextLoads() {
        log.info("info test");
        log.error("error test");
        log.debug("debug test");
        log.warn("warn test");
    }
}