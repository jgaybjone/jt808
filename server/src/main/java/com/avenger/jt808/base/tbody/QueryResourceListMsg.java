package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.enums.ResourceType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import com.avenger.jt808.util.LocalDateTimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/18.
 * Description:
 */
@WritingMessageType(type = (0x9205 - 0x10000))
@Data
public class QueryResourceListMsg implements Body {

    private byte channelId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private int alarmDetail;
    private int alarmExt;
    private ResourceType resourceType;
    private BitStreamType bitStreamType;
    private MemType memType;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return ByteBufUtils.array(Unpooled.buffer(24)
                .writeByte(channelId)
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(startAt, "yy-MM-dd-HH-mm-ss")))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(endAt, "yy-MM-dd-HH-mm-ss")))
                .writeInt(alarmDetail)
                .writeInt(alarmExt)
                .writeByte(resourceType.ordinal())
                .writeByte(bitStreamType.ordinal())
                .writeByte(memType.ordinal()));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum BitStreamType {
        ALL,
        MAIN,
        SUB;
    }

    public enum MemType {
        ALL,
        MAIN,
        DISASTER_RECOVERY;
    }

}
