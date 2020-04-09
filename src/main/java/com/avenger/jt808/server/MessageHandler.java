package com.avenger.jt808.server;

import com.avenger.jt808.base.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        final Channel channel = ctx.channel();
        TermConnManager.addConn(msg.getHeader().getSimNo(), channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        TermConnManager.connBreak(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        final String simNo = TermConnManager.simNoOnChannel(ctx.channel());
        log.error("客户端" + simNo + "连接异常", cause);
    }
}
