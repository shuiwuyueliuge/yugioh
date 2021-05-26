package cn.mayu.yugioh.prometheus.port.adapter.stomp.user;

/**
 * @description: 服务信息
 * @author: YgoPlayer
 * @time: 2021/5/26 2:03 下午
 */
public interface ServerInfoProvider {

    String getSessionId();

    String getServerName();

    String getDestination();
}
