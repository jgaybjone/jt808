package com.avenger.jt808.server;

import com.avenger.jt808.domain.Message;
import com.avenger.jt808.util.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Slf4j
@AllArgsConstructor
public class SimpleChannelMessageHandler extends SimpleChannelInboundHandler<Message> {

    private final MessageHandlerManager messageHandlerManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        final Channel channel = ctx.channel();
        TermConnManager.addConn(msg.getHeader().getSimNo(), channel);
        final Publisher<Message> process = messageHandlerManager.process(msg);
        Consumer<Message> consumer = m -> {
            if (channel.isActive()) {
                channel.writeAndFlush(m);
            } else {
                log.error("channel is inactive id ：{} ，req : {}, resp : {}",
                        channel.id().asLongText(), JsonUtils.objToJsonStr(msg), JsonUtils.objToJsonStr(m));
            }
        };
        if (process instanceof Flux) {
            ((Flux<Message>) process).filter(Objects::nonNull).subscribe(consumer,
                    err -> log.error("has error", err));
        } else if (process instanceof Mono) {
            ((Mono<Message>) process).filter(Objects::nonNull).subscribe(consumer,
                    err -> log.error("has error", err));
        } else {
            log.warn("未知类型不处理，class :{}", process.getClass());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("channel is inactive id ：{} ", ctx.channel().id().asLongText());
        TermConnManager.connBreak(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        final String simNo = TermConnManager.simNoOnChannel(ctx.channel());
        log.error("客户端" + simNo + "连接异常", cause);
    }
}
