package cn.mayu.yugioh.pegasus.application.query;

import lombok.Data;

@Data
public class CardInfoQuery {

    private Integer page;

    private Integer pageSize;

    private String dataCenter;
}
