package cn.mayu.yugioh.pegasus.application.datacenter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum EventEnum {

    CARD("card-meta"),
    INCLUDE("include-meta");

    private String type;
}
