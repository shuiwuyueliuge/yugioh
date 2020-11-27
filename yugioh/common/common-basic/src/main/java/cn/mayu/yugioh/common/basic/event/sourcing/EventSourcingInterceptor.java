package cn.mayu.yugioh.common.basic.event.sourcing;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;

@Aspect
public class EventSourcingInterceptor {

    public EventStore eventStore;

    public EventSourcingInterceptor(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Before("@annotation(cn.mayu.yugioh.common.basic.event.sourcing.EventSourcing)")
    public void before(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Event event = new Event(method.getName(), point.getArgs());
        eventStore.store(event);
    }
}
