package com.mayu.yugioh.common.web.reactive.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageRestResult extends RestResult {

    private Long total;

    public PageRestResult(int code, Pageable<?> pageableValue, String msg) {
        super(code, pageableValue.getData(), msg);
        this.total = pageableValue.getTotal();
    }
}
