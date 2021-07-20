package cn.mayu.yugioh.gateway.route.application;

import cn.mayu.yugioh.common.basic.domain.RemoteDomainEvent;
import cn.mayu.yugioh.common.basic.tool.JsonCreator;
import cn.mayu.yugioh.common.facade.hermes.EventFacade;
import cn.mayu.yugioh.common.facade.hermes.commond.EventReceiveCommand;
import cn.mayu.yugioh.common.web.handler.RestResult;
import cn.mayu.yugioh.gateway.route.domain.route.RouteEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventSubscribe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @description: 路由事件订阅
 * @author: YgoPlayer
 * @time: 2021/5/11 3:23 下午
 */
@Slf4j
@Component
@AllArgsConstructor
public class RouteEventSubscribe implements DomainEventSubscribe<RouteEvent> {

    private final DiscoveryClient discoveryClient;

    private final RestTemplate restTemplate;

    private final EventFacade eventFacade;

    private static final String ROUTE_URL = "http://%s:%s/route";

    private static final ConcurrentHashMap<String, ThreadPoolExecutor> POOL_EXECUTOR_MAP = new ConcurrentHashMap<>();

    private static final int THREAD_NUM = 1;

    private static final long ALIVE_TIME = 0L;

    private static final int QUEUE_COUNT = 1000;

    @Override
    public void subscribe(RouteEvent mainEvent) {
        List<ServiceInstance> instanceList = discoveryClient.getInstances(mainEvent.getName());
        if (Objects.isNull(instanceList) || instanceList.isEmpty()) {
            throw new ServiceInstanceNotFoundException(500, "找不到服务实例");
        }

        ThreadPoolExecutor executor = POOL_EXECUTOR_MAP.computeIfAbsent(mainEvent.getName(), this::init);
        executor.execute(new RouteTaskRunnable(mainEvent, instanceList, restTemplate, eventFacade));
    }

    private ThreadPoolExecutor init(String name) {
        return new ThreadPoolExecutor(
                THREAD_NUM,
                THREAD_NUM,
                ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(QUEUE_COUNT),
                buildFactory(name)
        );
    }

    private ThreadFactory buildFactory(String name) {
        return r -> {
            Thread thread = new Thread(r);
            thread.setName(name + "-route-thread");
            return thread;
        };
    }

    @Override
    public Class<RouteEvent> domainEventClass() {
        return RouteEvent.class;
    }

    @AllArgsConstructor
    private static class RouteTaskRunnable implements Runnable, Comparable<RouteTaskRunnable> {

        private final RouteEvent mainEvent;

        private final List<ServiceInstance> instanceList;

        private final RestTemplate restTemplate;

        private final EventFacade eventFacade;

        @Override
        public void run() {
            String content = JsonCreator.defaultInstance().writeValueAsString(mainEvent.getRouteDefinition());
            instanceList.stream()
                    .map(this::buildUrl)
                    .map(url -> postRoute(url, content))
                    .forEach(restResult -> sendPostResult(restResult, mainEvent.getOperateChannel()));
        }

        private String buildUrl(ServiceInstance serviceInstance) {
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();
            return String.format(ROUTE_URL, host, port);
        }

        private RestResult postRoute(String url, String content) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(content, headers);
            return restTemplate.postForObject(url, request, RestResult.class);
        }

        private void sendPostResult(RestResult restResult, String routingKey) {
            RemoteDomainEvent runDomainEvent = new RemoteDomainEvent(
                    System.currentTimeMillis(),
                    "async-message-in-0",
                    (!Objects.isNull(restResult) && restResult.getCode() == 200) ? "success" : "failure",
                    routingKey
            );

            eventFacade.receiveEvent(new EventReceiveCommand(runDomainEvent));
        }

        /**
         * 版本小得在前面
         *
         * @param o 待比较的对象
         * @return 排序
         */
        @Override
        public int compareTo(RouteTaskRunnable o) {
            return Integer.compare(this.mainEvent.getVersion(), o.mainEvent.getVersion());
        }
    }
}