package cn.mayu.yugioh.basic.gateway.daedalus.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import java.util.function.BiConsumer;

@Slf4j
@Service
@AllArgsConstructor
public class DynamicRouteCommandService implements ApplicationEventPublisherAware {

    private final RouteDefinitionRepository definitionRepository;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public Mono<Void> operate(RouteInfo routeInfo) {
        log.info("receive route info :{}", routeInfo);
        return new MonoRoute(routeInfo, definitionRepository, publisher);
    }

    @Data
    public static class RouteInfo {

        private RouteDefinition routeInfo;

        private OperateEnum routeStatus;
    }

    private enum OperateEnum {

        CREATE {
            @Override
            protected BiConsumer<RouteDefinition, RouteDefinitionRepository> routeDefinitionConsumer() {
                return (definition, definitionRepository) -> definitionRepository.save(Mono.just(definition)).subscribe();
            }
        },

        UPDATE {
            @Override
            protected BiConsumer<RouteDefinition, RouteDefinitionRepository> routeDefinitionConsumer() {
                return (definition, definitionRepository) -> {
                    definitionRepository.delete(Mono.just(definition.getId()));
                    definitionRepository.save(Mono.just(definition)).subscribe();
                };
            }
        },

        DELETE {
            @Override
            protected BiConsumer<RouteDefinition, RouteDefinitionRepository> routeDefinitionConsumer() {
                return (definition, definitionRepository) -> definitionRepository.delete(Mono.just(definition.getId())).subscribe();
            }
        };

        public final void operate(
                RouteDefinition definition,
                RouteDefinitionRepository definitionRepository,
                ApplicationEventPublisher publisher
        ) {
            routeDefinitionConsumer().accept(definition, definitionRepository);
            publisher.publishEvent(new RefreshRoutesEvent(this));
        }

        protected BiConsumer<RouteDefinition, RouteDefinitionRepository> routeDefinitionConsumer() {
            throw new IllegalArgumentException();
        }
    }

    @AllArgsConstructor
    public static class MonoRoute extends Mono<Void> {

        final RouteInfo routeInfo;

        final RouteDefinitionRepository definitionRepository;

        final ApplicationEventPublisher publisher;

        @Override
        public void subscribe(CoreSubscriber<? super Void> actual) {
            routeInfo.routeStatus.operate(routeInfo.getRouteInfo(), definitionRepository, publisher);
            Operators.complete(actual);
        }
    }
}