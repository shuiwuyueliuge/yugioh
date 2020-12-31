package cn.mayu.yugioh.pegasus.infrastructure.datacenter;

import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;

import java.util.List;

public interface IncludeData extends DataType {

    List<IncludeDTO> obtainIncludes(String password, String source);
}
