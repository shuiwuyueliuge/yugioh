package cn.mayu.yugioh.pegasus.application.datacenter;

import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import java.util.List;

public interface IncludeData extends DataType {

    List<IncludeDTO> data2IncludeDTO(String cardData);
}