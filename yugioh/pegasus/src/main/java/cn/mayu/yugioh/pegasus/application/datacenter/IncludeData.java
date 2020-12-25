package cn.mayu.yugioh.pegasus.application.datacenter;

import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.MetaData;
import java.util.List;

public interface IncludeData extends DataType {

    List<MetaData<IncludeDTO>> obtainIncludes(String password, String source);
}
