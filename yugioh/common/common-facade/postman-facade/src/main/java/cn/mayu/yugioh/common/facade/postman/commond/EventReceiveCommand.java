package cn.mayu.yugioh.common.facade.postman.commond;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventReceiveCommand {

    private DomainEvent domainEvent;
}
