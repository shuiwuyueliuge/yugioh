package cn.mayu.yugioh.common.basic.domain;

public interface ValidationNotificationHandler {

    void handleError(String aNotificationMessage);

    void handleError(String aNotification, Object anObject);
}