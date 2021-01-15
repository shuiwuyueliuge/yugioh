package cn.mayu.yugioh.common.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageRestResult extends RestResult {

    private Long total;

    protected PageRestResult(int code, Pageable<?> pageableValue) {
        super(code, pageableValue.getData());
        this.total = pageableValue.getTotal();
    }
}
