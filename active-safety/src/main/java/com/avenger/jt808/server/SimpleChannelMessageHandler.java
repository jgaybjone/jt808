package com.avenger.jt808.server;

import com.avenger.jt808.domain.Message;
import com.avenger.jt808.util.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class SimpleChannelMessageHandler extends SimpleChannelInboundHandler<Message> {

    private final MessageHandlerManager messageHandlerManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        log.info("处理消息：{}", JsonUtils.objToJsonStr(msg));
        final Channel channel = ctx.channel();
        final Publisher<Message> process = messageHandlerManager.process(msg);
        Consumer<Message> consumer = m -> {
            log.info("处理完毕返回消息：{}", JsonUtils.objToJsonStr(m));
            if (channel.isActive()) {
                channel.writeAndFlush(m);
            } else {
                log.error("channel is inactive id ：{} ，req : {}, resp : {}",
                        channel.hashCode(), JsonUtils.objToJsonStr(msg), JsonUtils.objToJsonStr(m));
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
        log.info("channel is inactive id ：{} ", ctx.channel().hashCode());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("客户端连接异常", cause);
    }
}