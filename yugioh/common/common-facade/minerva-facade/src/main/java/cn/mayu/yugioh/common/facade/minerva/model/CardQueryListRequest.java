package cn.mayu.yugioh.common.facade.minerva.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardQueryListRequest {

    private String dataCenter;

    private String searchCondition;

    private int page;

    private int pageSize;
}
