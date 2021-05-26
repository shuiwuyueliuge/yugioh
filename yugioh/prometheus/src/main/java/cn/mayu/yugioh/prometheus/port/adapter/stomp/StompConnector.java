package cn.mayu.yugioh.prometheus.port.adapter.stomp;

import cn.mayu.yugioh.prometheus.port.adapter.stomp.user.ServerInfoProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.stomp.BufferingStompDecoder;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompDecoder;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 链接stomp服务
 * @author: YgoPlayer
 * @time: 2021/5/26 1:32 下午
 */
@Configuration
public class StompConnector {

    private final MessageChannel clientInboundChannel;

    private final BufferingStompDecoder decoder;

    private static final int BUFFER_SIZE_LIMIT = 65536;

    private final ServerInfoProvider serverInfoProvider;

    private String connectMsg = "CONNECT\n" +
                                "accept-version:1.1,1.0\n" +
                                "heart-beat:10000,10000\n" +
                                "\n" +
                                "\u0000";

    private String pingMsg = "\n";

    public StompConnector(MessageChannel clientInboundChannel, ServerInfoProvider serverInfoProvider) {
        this.clientInboundChannel = clientInboundChannel;
        this.serverInfoProvider = serverInfoProvider;
        this.decoder = new BufferingStompDecoder(new StompDecoder(), BUFFER_SIZE_LIMIT);
    }

    public void conn() {
        sendMsg(connectMsg);
    }

    public void sub() {
        String subMsg = "SUBSCRIBE\n" +
                "id:sub-0\n" +
                "destination:/user" + serverInfoProvider.getDestination() + "\n" +
                "\n" +
                "\u0000";
        sendMsg(subMsg);
    }

    public void ping() {
        sendMsg(pingMsg);
    }

    private void sendMsg(String msg) {
        ByteBuffer bf = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        List<Message<byte[]>> messages = decoder.decode(bf);
        Message<byte[]> message = messages.get(0);
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Assert.state(headerAccessor != null, "No StompHeaderAccessor");
        try {
            headerAccessor.setSessionId(serverInfoProvider.getSessionId());
            headerAccessor.setSessionAttributes(new HashMap<>());
            headerAccessor.setUser(serverInfoProvider::getServerName);
            headerAccessor.setHeader("simpHeartbeat", headerAccessor.getHeartbeat());
            try {
                SimpAttributesContextHolder.setAttributesFromMessage(message);
                clientInboundChannel.send(message);
            } finally {
                SimpAttributesContextHolder.resetAttributes();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
