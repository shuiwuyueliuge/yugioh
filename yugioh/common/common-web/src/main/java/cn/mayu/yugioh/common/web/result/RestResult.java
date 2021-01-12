package cn.mayu.yugioh.common.web.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.MDC;

@Data
public class RestResult {

    private int code;

    private Object data;

    private String msg;

    private String traceId;

    public RestResult(int code, Object data) {
        this(code, data, "");
    }

    public RestResult(int code, String msg) {
        this(code, null, msg);
    }

    public RestResult(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.traceId = MDC.get("traceId");
    }

    public RestResult(Object o) {
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
