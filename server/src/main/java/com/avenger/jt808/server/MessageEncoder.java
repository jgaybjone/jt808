package com.avenger.jt808.server;

import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.entity.MessageRecord;
import com.avenger.jt808.enums.MessageFlow;
import com.avenger.jt808.enums.MessageRecordStatus;
import com.avenger.jt808.service.MessageRecordService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 消息序列化处理
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
@Slf4j
@AllArgsConstructor
public class MessageEncoder extends MessageToByteEncoder<Message> {

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    private final MessageRecordService messageRecordService;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    public static String writeAsString(Object o) {
        String s = null;
        try {
            s = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("msg to json fail", e);
        }
        return s;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        final ByteBuf buffer = Unpooled.buffer(200);
        final Header header = msg.getHeader();
        final WritingMessageType type = msg.getMsgBody().getClass().getAnnotation(WritingMessageType.class);
        if (type.needReply()) {
            reactiveRedisTemplate
                    .opsForValue()
                    .set(header.getSimNo() + "::" + header.getSerialNo(), msg, Duration.ofMinutes(60))
                    .doOnSuccess(c -> messageRecordService.saveEntity(MessageRecord
                            .builder()
                            .messageType((int) header.getId())
                            .serialNo(((int) header.getSerialNo()))
                            .simNo(header.getSimNo())
                            .flowTo(MessageFlow.SEND)
                            .status(MessageRecordStatus.NOT_RESPONDING)
                            .detail(writeAsString(msg))
                            .build()))
                    .subscribeOn(Schedulers.parallel())
                    .subscribe();
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
