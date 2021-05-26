package cn.mayu.yugioh.common.facade.hermes.commond;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventReceiveCommand {

    private RemoteDomainEvent domainEvent;
}
