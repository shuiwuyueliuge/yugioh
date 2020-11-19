package cn.mayu.yugioh.common.basic.exception;

public class AbstractException extends RuntimeException {

    private Exception sourceException;

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Exception sourceException) {
        super(message);
        this.sourceException = sourceException;
    }
}
