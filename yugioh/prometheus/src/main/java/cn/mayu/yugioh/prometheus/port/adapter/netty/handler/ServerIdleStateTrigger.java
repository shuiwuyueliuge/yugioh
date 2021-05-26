package cn.mayu.yugioh.prometheus.port.adapter.netty.handler;

import cn.mayu.yugioh.prometheus.port.adapter.netty.ChannelSupervise;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @description: 心跳超时断线
 * @author: YgoPlayer
 * @time: 2021/5/17 4:26 下午
 */
public class ServerIdleStateTrigger extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
           // IdleState state = ((IdleStateEvent) evt).state();
            //if (state == IdleState.READER_IDLE) {
            ctx.disconnect();
            ChannelSupervise.removeChannel(ctx.channel());
            //}

            return;
        }

        super.userEventTriggered(ctx, evt);
    }
}
