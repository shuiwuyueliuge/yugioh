package cn.mayu.yugioh.pegasus.infrastructure.datacenter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventEnum {

    CARD("card-meta"),
    INCLUDE("include-meta");

    private String type;
}
