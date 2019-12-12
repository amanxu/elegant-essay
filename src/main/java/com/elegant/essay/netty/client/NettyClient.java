package com.elegant.essay.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxu.nie
 * @description: Netty服务器客户端
 */
@Slf4j
public class NettyClient {

    private final static Integer maxTryTime = 3;
    private int reTryTime = 0;

    public void connect(String host, int port) {
        log.debug("Netty Client Starting...");
        // 配置客户端的NIO线程组
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            // Client辅助启动类
            Bootstrap bootstrap = new Bootstrap();
            // 配置bootstrap
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 解码编码
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            // 心跳机制
                            ch.pipeline().addLast(new IdleStateHandler(30, 10, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            // 发起异步连接操作  同步方法待成功
            ChannelFuture future = bootstrap.connect(host, port).sync();
            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e1) {
                log.error("Netty Client InterruptedException:{}", e);
            }
            log.warn("Client connect server {}:{} fail", host, port);
            connect(host, port);
        } finally {
            // 优雅退出，释放NIO线程组
            clientGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        nettyClient.connect("127.0.0.1", 8090);
    }
}
