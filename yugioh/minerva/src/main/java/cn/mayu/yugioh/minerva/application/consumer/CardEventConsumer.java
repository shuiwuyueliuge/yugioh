package cn.mayu.yugioh.minerva.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
//import cn.mayu.yugioh.common.facade.minerva.model.CardDTO;
import cn.mayu.yugioh.minerva.application.packages.CardPackageCommandService;
import cn.mayu.yugioh.minerva.application.packages.command.PackCreateCommand;
import cn.mayu.yugioh.minerva.domain.aggregate.card.CardCreated;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 消费卡片创建事件
 */
@Component
public class CardEventConsumer implements DomainEventSubscribe<CardCreated> {

//    @Autowired
//    private EventFacade eventFacade; es服务 弃用

    @Autowired
    private CardPackageCommandService packageCommandService;

    @Override
    public void subscribe(CardCreated cardCreated) {
        // 检查是否有卡包，创建卡包
        if (!CollectionUtils.isEmpty(cardCreated.getInclude())) {
            cardCreated.getInclude().forEach(include -> {
                PackCreateCommand packCreateCommand = new PackCreateCommand(
                        include.getPackageIdentity().getPackageName(),
                        include.getIncludeTime(),
                        include.getPackageIdentity().getPackShortName(),
                        "",
                        Lists.newArrayList(cardCreated.getCardIdentity().getPassword())
                );
                packageCommandService.createPackage(packCreateCommand);
            });
        }

        // es服务 弃用
//        CardDTO cardDTO = new CardDTO(
//                cardCreated.getName(),
//                cardCreated.getDesc(),
//                cardCreated.getTypeVal(),
//                cardCreated.getLink(),
//                cardCreated.getDef(),
//                cardCreated.getPend(),
//                cardCreated.getRace(),
//                cardCreated.getCardIdentity().getPassword(),
//                cardCreated.getAttribute(),
//                cardCreated.getLevel(),
//                cardCreated.getAtk(),
//                cardCreated.getTypeSt(),
//                cardCreated.getLinkArrow()
//        );
//
//        RemoteDomainEvent domainEvent = new RemoteDomainEvent(
//                cardCreated.occurredOn(),
//                cardCreated.getEventType(),
//                JsonConstructor.defaultInstance().writeValueAsString(cardDTO),
//                cardDTO.getPassword()
//        );
//
//        eventFacade.receiveEvent(new EventReceiveCommand(domainEvent));
    }

    @Override
    public Class<CardCreated> domainEventClass() {
        return CardCreated.class;
    }
}
