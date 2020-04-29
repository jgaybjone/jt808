package com.avenger.jt808.server;

import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.WritingMessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import java.time.Duration;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 消息序列化处理
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
@AllArgsConstructor
public class MessageEncoder extends MessageToByteEncoder<Message> {

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        final ByteBuf buffer = Unpooled.buffer(200);
        final Header header = msg.getHeader();
        final WritingMessageType type = msg.getMsgBody().getClass().getAnnotation(WritingMessageType.class);
        if (type.needReply()) {
            reactiveRedisTemplate.opsForValue().set(header.getSimNo() + "::" + header.getSerialNo(), msg, Duration.ofMinutes(2));
        }
        header.setId(type.type());
        final byte[] b = msg.getMsgBody().serialize();
        buffer.writeBytes(header.getRaw((byte) b.length));
        buffer.writeBytes(b);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("7e");
        out.writeByte(0x7e);
        int check = 0;
        while (buffer.isReadable()) {
            final byte c = buffer.readByte();
            check = check ^ c;
            switch (c) {
                case 0x7D:
                    out.writeShort(0x7D01);
                    stringBuilder.append("7D01");
                    break;
                case 0x7E:
                    out.writeShort(0x7D02);
                    stringBuilder.append("7D02");
                    break;
                default:
                    out.writeByte(c);
                    final String hex = Integer.toHexString(c);
                    if (hex.length() < 2) {
                        stringBuilder.append(0);
                    }
                    stringBuilder.append(hex);
                    break;
            }
        }

        out.writeByte(check);
        final String hex = Integer.toHexString(check);
        if (hex.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hex);
        out.writeByte(0x7e);
        stringBuilder.append("7e");
        if (log.isDebugEnabled()) {
            log.debug("out bytes: {}", stringBuilder.toString().replaceAll("ffffff", ""));
        }
    }
}
