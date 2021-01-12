package cn.mayu.yugioh.common.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageRestResult extends RestResult {

    private Long total;

    private Integer pageNum;

    private Integer pageSize;

    public PageRestResult(int code, Object data, Long total, Integer pageNum, Integer pageSize) {
        super(code, data);
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
