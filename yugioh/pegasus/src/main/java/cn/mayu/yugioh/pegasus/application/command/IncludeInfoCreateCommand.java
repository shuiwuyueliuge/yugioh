package cn.mayu.yugioh.pegasus.application.command;

import cn.mayu.yugioh.pegasus.infrastructure.datacenter.DataCenterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncludeInfoCreateCommand {

    private DataCenterEnum dataCenter;

    private String channelId;

    private String cardPassword;

    private String resource;
}
