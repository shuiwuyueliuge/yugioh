package cn.mayu.yugioh.pegasus.port.adapter.datacenter.html;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HtmlHandlerException extends RuntimeException {

    private int statusCode;

    private String url;
}
