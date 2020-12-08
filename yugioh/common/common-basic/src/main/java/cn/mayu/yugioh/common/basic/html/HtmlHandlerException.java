package cn.mayu.yugioh.common.basic.html;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HtmlHandlerException extends RuntimeException {

    private int statusCode;

    private String url;
}
