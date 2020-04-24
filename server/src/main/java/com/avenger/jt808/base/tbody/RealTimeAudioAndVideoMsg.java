package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.BitstreamType;
import com.avenger.jt808.enums.RealTimeDataType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/18.
 * Description: 实时音视频传输指令
 */
@WritingMessageType(type = ((byte) 0x9101))
@Data
public class RealTimeAudioAndVideoMsg implements Body {

    private String ip;

    private short tcpPort;

    private short udpPort;

    /**
     * 逻辑通道号
     */
    private byte channel;

    private RealTimeDataType dataType;

    private BitstreamType bitstreamType;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return Unpooled.buffer(30)
                .writeByte(ip.length())
                .writeBytes(ip.getBytes(Charset.forName("GBK")))
                .writeShort(tcpPort)
                .writeShort(udpPort)
                .writeByte(channel)
                .writeByte(dataType.ordinal())
                .writeByte(bitstreamType.ordinal()).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
