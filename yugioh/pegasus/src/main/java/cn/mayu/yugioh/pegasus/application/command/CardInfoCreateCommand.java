package cn.mayu.yugioh.pegasus.application.command;

import cn.mayu.yugioh.pegasus.application.datacenter.DataCenterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoCreateCommand {

    private DataCenterEnum dataCenter;

    private String channelId;
}
