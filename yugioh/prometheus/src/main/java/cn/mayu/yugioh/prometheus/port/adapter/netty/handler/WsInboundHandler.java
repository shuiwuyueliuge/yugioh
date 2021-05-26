package cn.mayu.yugioh.prometheus.port.adapter.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: ws消息处理
 * @author: YgoPlayer
 * @time: 2021/5/25 5:28 下午
 */
@Slf4j
public class WsInboundHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        Attribute<WebSocketServerHandshaker> a = ctx.channel().attr(AttributeKey.valueOf("WebSocketServerHandshaker"));
        WebSocketServerHandshaker handShaker = a.get();

        if (frame instanceof CloseWebSocketFrame) {
            handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            a.set(null);
            return;
        }

        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof BinaryWebSocketFrame) {
            return;
        }

        if (frame instanceof TextWebSocketFrame) {
            String msg = ((TextWebSocketFrame) frame).text();
            if (log.isDebugEnabled()) {
                log.debug("receive:{}", msg);
            }

            String res = ctx.channel().id().asShortText();
            if (log.isDebugEnabled()) {
                log.debug("send:{}", res);
            }

            ctx.channel().writeAndFlush(new TextWebSocketFrame("dasdasdasdasdasdasdasdasdsadasdasdasdas"));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
