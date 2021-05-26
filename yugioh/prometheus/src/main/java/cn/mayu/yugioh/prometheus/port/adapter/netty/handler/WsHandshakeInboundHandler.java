package cn.mayu.yugioh.prometheus.port.adapter.netty.handler;

import cn.mayu.yugioh.prometheus.port.adapter.netty.ChannelSupervise;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import static io.netty.handler.codec.http.HttpHeaderNames.UPGRADE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @description: websocket握手处理
 *               websocket握手协议使用http(s)
 *               用户登陆成功后链接ws服务，客户端每隔10s发起内容为'\n'的ws心跳。
 *               ws服务器保存用户的绘话信息。
 *               10s内无心跳包删除用户绘话信息。
 *               每台ws服务绑定一个stomp服务，通过channel_id找到stomp的队列发送信息到指定的ws服务器，
 *               ws服务根据用户id找对对应的channel，发送数据。
 * @author: YgoPlayer
 * @time: 2021/5/25 4:08 下午
 */
public class WsHandshakeInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String wsUri = "/prometheus";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // TODO gateway authentication
        String uri = req.uri();
        if (wsUri.equals(uri) && req.decoderResult().isSuccess() && ("websocket".equals(req.headers().get(UPGRADE)))) {
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(wsUri, null, false);
            WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(req);
            if (handShaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handShaker.handshake(ctx.channel(), req);
                Attribute<WebSocketServerHandshaker> attribute = ctx.channel().attr(AttributeKey.valueOf("WebSocketServerHandshaker"));

                attribute.set(handShaker);
            }

            ChannelSupervise.addChannel(ctx.channel());
            return;
        }

        sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            try {
                res.content().writeBytes(buf);
            } finally {
                buf.release();
            }
        }

        ChannelFuture future = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
