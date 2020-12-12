package cn.mayu.yugioh.library.port.adapter.mq;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonParser;
import cn.mayu.yugioh.library.application.card.CardCommandService;
import cn.mayu.yugioh.library.application.card.command.CardCreateCommand;
import cn.mayu.yugioh.library.application.include.IncludeCommandService;
import cn.mayu.yugioh.library.application.include.command.IncludeCreateCommand;
import com.rabbitmq.client.Channel;
import lombok.Getter;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.*;

@Component
public class MqDataThreadPool {

    private ThreadPoolExecutor[] threadPoolExecutors;

    private CardCommandService cardCommandService;

    private IncludeCommandService includeCommandService;

    public MqDataThreadPool(MqThreadPoolConfig mqThreadPoolConfig,
                            CardCommandService cardCreateCommandService,
                            IncludeCommandService includeCommandService) {
        this.includeCommandService = includeCommandService;
        this.cardCommandService = cardCreateCommandService;
        threadPoolExecutors = new ThreadPoolExecutor[mqThreadPoolConfig.getCount()];
        for (int i = 0; i < threadPoolExecutors.length; i++) {
            threadPoolExecutors[i] = new ThreadPoolExecutor(1,
                    1,
                    0L,
                    TimeUnit.SECONDS,
                    new PriorityBlockingQueue<>(mqThreadPoolConfig.getQueueSize()),
                    new MqThreadFactory(mqThreadPoolConfig.getThreadNamePrefix() + i));
        }
    }

    public void put(final Message<String> message) {
        String payload = message.getPayload();
        DomainEvent domainEvent = JsonParser.defaultInstance().readObjectValue(payload, DomainEvent.class);
        int index = domainEvent.getRoutingKey().hashCode() % threadPoolExecutors.length;
        if (index < 0) {
            index = index * -1;
        }

        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutors[index];
        threadPoolExecutor.execute(new Task(message, domainEvent));
    }

    private class MqThreadFactory implements ThreadFactory {

        private String name;

        public MqThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(name);
            return thread;
        }
    }

    @Getter
    private class Task implements Runnable, Comparable {

        private Message<String> message;

        private DomainEvent domainEvent;

        public Task(Message<String> message, DomainEvent domainEvent) {
            this.message = message;
            this.domainEvent = domainEvent;
        }

        @Override
        public int compareTo(Object o) {
            Task other = (Task) o;
            Long thisTime = domainEvent.getOccurredOn();
            Long otherTime = other.getDomainEvent().getOccurredOn();
            if (thisTime > otherTime) {
                return 1;
            }

            if (thisTime < otherTime) {
                return -1;
            }

            return 0;
        }

        @Override
        public void run() {
            if ("card".equals(domainEvent.getType())) {
                CardCreateCommand cardCreateCommand = JsonParser.builder().failOnUnknownProperties(false).build().readObjectValue(domainEvent.getBody().toString(), CardCreateCommand.class);
                cardCommandService.createCard(cardCreateCommand);
            }

            if ("include".equals(domainEvent.getType())) {
                IncludeCreateCommand includeCreateCommand = JsonParser.builder().failOnUnknownProperties(false).build().readObjectValue(domainEvent.getBody().toString(), IncludeCreateCommand.class);
                includeCommandService.createInclude(includeCreateCommand);
            }

            if ("package".equals(domainEvent.getType())) {
            }

            basicAck(message);
        }

        private void basicAck(Message<String> message) {
            Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
            Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
            try {
                channel.basicAck(deliveryTag, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
