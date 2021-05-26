package cn.mayu.yugioh.prometheus.port.adapter.netty;

import cn.mayu.yugioh.prometheus.port.adapter.netty.handler.WsHandshakeInboundHandler;
import cn.mayu.yugioh.prometheus.port.adapter.netty.handler.WsInboundHandler;
import cn.mayu.yugioh.prometheus.port.adapter.stomp.BrokerLinkFinishedEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

@Slf4j
public class WsServer implements ApplicationListener<BrokerLinkFinishedEvent> {

    @Value("${server.port}")
    private Integer port;

    @Override
    public void onApplicationEvent(BrokerLinkFinishedEvent brokerLinkFinishedEvent) {
        new Thread(() -> startWsServer(port)).start();
    }

    public static void startWsServer(int port) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChannelInitializer());
            Channel channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty server start error", e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    private static class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) {
            EventExecutorGroup executorGroup = new NioEventLoopGroup();
            ch.pipeline()
                    .addLast("logging", new LoggingHandler(LogLevel.DEBUG)) // 设置log监听器，并且日志级别为debug，方便观察运行流程
//                    .addLast("idle-handler", new IdleStateHandler(3, 30, 30))
//                    .addLast("idle-trigger", new ServerIdleStateTrigger())
                    .addLast("http-codec", new HttpServerCodec()) // 设置解码器
                    .addLast("aggregator", new HttpObjectAggregator(65536)) // 聚合器，使用websocket会用到
                    .addLast("http-chunked", new ChunkedWriteHandler()) // 用于较大数据的分区传输
                    .addLast(executorGroup, "handshake", new WsHandshakeInboundHandler())
                    .addLast(executorGroup, "handler", new WsInboundHandler());
                    //.addLast(new NioEventLoopGroup(), "handler", new WebSocketService()); // 自定义的业务handler
        }
    }
}
