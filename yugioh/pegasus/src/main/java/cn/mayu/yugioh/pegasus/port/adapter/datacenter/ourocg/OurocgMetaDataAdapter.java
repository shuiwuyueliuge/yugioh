package cn.mayu.yugioh.pegasus.port.adapter.datacenter.ourocg;

import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterEnum;
import cn.mayu.yugioh.pegasus.infrastructure.datacenter.EventEnum;
import cn.mayu.yugioh.pegasus.application.dto.CardDTO;
import cn.mayu.yugioh.pegasus.application.dto.IncludeDTO;
import cn.mayu.yugioh.pegasus.domain.aggregate.*;

public class OurocgMetaDataAdapter {

    public static MetaData<CardDTO> changeCard2MetaData(String password, CardDTO changeObj) {
        MetaDataIdentity metaDataIdentity = new MetaDataIdentity(
                password, DataCenterEnum.OUROCG.toString(), EventEnum.CARD.getType());
        return new MetaData(metaDataIdentity, changeObj);
    }

    public static MetaData<IncludeDTO> changeInclude2MetaData(String password, IncludeDTO includeStr) {
        MetaDataIdentity metaDataIdentity = new MetaDataIdentity
                (password, DataCenterEnum.OUROCG.toString(), EventEnum.INCLUDE.getType());
        return new MetaData(metaDataIdentity, includeStr);
    }
}
