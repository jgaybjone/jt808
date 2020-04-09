package com.avenger.jt808.server;

import com.avenger.jt808.base.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 终端连接管理
 */
@UtilityClass
@Slf4j
public class TermConnManager {
    private final static Map<String, Channel> CHANNEL_HOLDER = new ConcurrentHashMap<>();
    private final static AttributeKey<String> SIM_KEY = AttributeKey.valueOf("simNo");

    public static void addConn(String simNo, Channel channel) {
        channel.attr(SIM_KEY).set(simNo);
        CHANNEL_HOLDER.put(simNo, channel);
    }

    public static void sendMessage(Message message, Consumer<Future<? super Void>> consumer) {
        final String simNo = message.getHeader().getSimNo();
        final Channel channel = CHANNEL_HOLDER.get(simNo);
        final ChannelFuture channelFuture = channel.writeAndFlush(message);
        if (consumer != null)
            channelFuture.addListener(consumer::accept);
    }

    public static void sendMessage(Message message) {
        sendMessage(message, null);
    }

    public static void connBreak(Channel channel) {
        final String simNo = channel.attr(SIM_KEY).get();
        if (!StringUtils.isEmpty(simNo)) {
            CHANNEL_HOLDER.remove(simNo);
        }
    }

    public static String simNoOnChannel(Channel channel) {
        return channel.attr(SIM_KEY).get();
    }

}
