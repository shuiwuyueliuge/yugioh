package cn.mayu.yugioh.prometheus.port.adapter.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChannelSupervise {

    private static final ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ConcurrentMap<String, ChannelId> ChannelMap = new ConcurrentHashMap<>();

    public static void addChannel(Channel channel) {
        GlobalGroup.add(channel);
        ChannelMap.put(channel.id().asShortText(), channel.id());
    }

    public static void removeChannel(Channel channel) {
        GlobalGroup.remove(channel);
        ChannelMap.remove(channel.id().asShortText());
    }

    public static Channel findChannel(String channelId) {
        ChannelId id = ChannelMap.get(channelId);
        if (id == null) {
            return null;
        }

        return GlobalGroup.find(ChannelMap.get(channelId));
    }

    public static void send2All(TextWebSocketFrame tws) {
        GlobalGroup.writeAndFlush(tws);
    }

    public static void send2One(String requestId, Object msg) {
        Channel channel = findChannel(requestId);
        channel.writeAndFlush(new TextWebSocketFrame(msg.toString()));
    }
}
