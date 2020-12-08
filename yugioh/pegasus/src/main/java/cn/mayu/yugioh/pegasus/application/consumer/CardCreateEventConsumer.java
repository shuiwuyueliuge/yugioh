package cn.mayu.yugioh.pegasus.application.consumer;

import cn.mayu.yugioh.common.basic.domain.DomainEvent;
import cn.mayu.yugioh.common.basic.domain.DomainEventConsumer;
import cn.mayu.yugioh.common.facade.postman.EventFacade;
import cn.mayu.yugioh.common.facade.postman.commond.EventReceiveCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardCreateEventConsumer implements DomainEventConsumer {

    @Autowired
    private EventFacade eventFacade;

    @Override
    public void subscribe(DomainEvent domainEvent) {
        eventFacade.receiveEvent(new EventReceiveCommand(domainEvent));
    }

    @Override
    public String getEventType() {
        return "card";
    }
}

//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        HttpEntity<String> request = new HttpEntity<>(JsonConstructor.defaultInstance().writeValueAsString(new EventReceiveCommand(domainEvent)), headers);
//        String url = "http://127.0.0.1:10201/event/receive";
//        ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, request, String.class);
//        String body = postForEntity.getBody();
//        System.out.println(body);


