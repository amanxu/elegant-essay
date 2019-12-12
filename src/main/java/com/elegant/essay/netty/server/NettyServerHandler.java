package com.elegant.essay.netty.server;

import com.elegant.essay.netty.constants.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaoxu.nie
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private int lossConnectCount = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }


    /**
     * 读事件
     *
     * @param ctx ChannelHandlerContext
     * @param msg 消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String body = (String) msg;
        log.info("[服务端接收消息] :{}", body);
        if (Constant.HEART_CHECK.equals(msg)) {
            log.debug("{} -> [心跳监测] {}：通道活跃", this.getClass().getName(), ctx.channel().id());
            // 心跳消息
            lossConnectCount = 0;
        }
        // 异步发送应答消息给Client
        ctx.writeAndFlush(String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 触发器
     *
     * @param channelHandlerContext channelHandlerContext
     * @param evt
     * @throws Exception exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object evt) throws Exception {
        log.debug("{} -> [已经有30秒中没有接收到客户端的消息了]", this.getClass().getName());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                if (lossConnectCount > 2) {
                    log.info("{} -> [释放不活跃通道] {}", this.getClass().getName(), channelHandlerContext.channel().id());
                    channelHandlerContext.channel().close();
                }
            }
        } else {
            super.userEventTriggered(channelHandlerContext, evt);
        }
    }
}
