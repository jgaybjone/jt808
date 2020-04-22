package com.avenger.jt808.base.tbody;

import com.avenger.jt808.annotation.WritingMessageType;
import com.avenger.jt808.base.Body;
import com.avenger.jt808.enums.ResourceType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.LocalDateTimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/19.
 * Description: 录像回放请求
 */
@WritingMessageType(type = ((byte) 0x9201))
@Data
public class VideoRelayMsg implements Body {

    private String ip;

    private short tcpPort;

    private short udpPort;

    private byte channelId;

    private ResourceType resourceType;
    private QueryResourceListMsg.BitStreamType bitStreamType;
    private QueryResourceListMsg.MemType memType;

    private RelayType relayType;
    /**
     * 快进或快退倍数
     */
    private byte forwardMultiple;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return Unpooled.buffer(33)
                .writeByte(ip.length())
                .writeBytes(ip.getBytes(Charset.forName("GBK")))
                .writeShort(tcpPort)
                .writeShort(udpPort)
                .writeByte(channelId)
                .writeByte(resourceType.ordinal())
                .writeByte(bitStreamType.ordinal())
                .writeByte(memType.ordinal())
                .writeByte(relayType.ordinal())
                .writeByte(forwardMultiple)
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(startAt, "yy-MM-dd-HH-mm-ss")))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(endAt, "yy-MM-dd-HH-mm-ss")))
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum RelayType {
        NORMAL,
        FAST_FORWARD,
        KEY_REWIND,
        KEY_PLAY,
        SINGLE_FRAME
    }
}
