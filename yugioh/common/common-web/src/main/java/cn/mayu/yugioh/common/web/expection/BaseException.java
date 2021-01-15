package cn.mayu.yugioh.common.web.expection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException {

    private int code;

    private String restMsg;

    private Exception sourceException;

    public BaseException(int code, String restMsg) {
        this(code, restMsg, null);
    }

    public BaseException(Exception sourceException) {
        this(0, null, sourceException);
    }

    public BaseException(int code, String restMsg, Exception sourceException) {
        super(sourceException.getMessage());
        this.code = code;
        this.sourceException = sourceException;
        this.restMsg = restMsg;
    }
}
