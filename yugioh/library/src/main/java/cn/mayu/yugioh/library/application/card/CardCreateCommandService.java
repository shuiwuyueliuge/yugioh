package cn.mayu.yugioh.library.application.card;

import cn.mayu.yugioh.library.application.card.command.CardCreateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import static cn.mayu.yugioh.library.config.AsyncConfig.ASYNC_EXECUTOR_NAME;

@Service
@Slf4j
public class CardCreateCommandService {

    @Async(ASYNC_EXECUTOR_NAME)
    public void createCard(CardCreateCommand cardCreateCommand) {
        log.info("{}", cardCreateCommand);
    }
}
