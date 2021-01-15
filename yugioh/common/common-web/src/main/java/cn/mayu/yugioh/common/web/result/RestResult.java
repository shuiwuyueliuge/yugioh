package cn.mayu.yugioh.common.web.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class RestResult {

    private int code;

    private Object data;

    private String msg;

    protected RestResult(int code, Object data) {
        this(code, data, null);
    }

    protected RestResult(int code, String msg) {
        this(code, null, msg);
    }

    protected RestResult(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    protected static Object thisOrString(Class<?> genericTypeClass, int successCode, Object responseValue) {
        return String.class == genericTypeClass ?
                new RestResult(successCode, responseValue).toString() :
                new RestResult(successCode, responseValue);
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
