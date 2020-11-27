package cn.mayu.yugioh.common.basic.event.sourcing;

public interface EventStore {

    void store(Event eventSource);
}
