package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.AlarmIdentification;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.LocalDateTimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/23.
 * Description:
 * 报警附件上传指令
 * 消息 ID:0x9208。
 * 报文类型:信令数据报文。 平台接收到带有附件的报警/事件信息后，向终端下发附件上传指令
 */
@WritingMessageType(type = ((byte) 0x9208))
@Data
public class UploadAlarmAccessoriesMsg implements Body {


    private String host;

    private short tcpPort;

    private short udpPort;

    private AlarmIdentification alarmIdentification;

    private String alarmNo;

    private byte[] reserve = new byte[16];

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        final ByteBuf buffer = Unpooled.buffer(40);
        final LocalDateTime date = alarmIdentification.getDate() == null ? LocalDateTime.now() : alarmIdentification.getDate();

        return buffer.writeByte(host.length())
                .writeBytes(host.getBytes(Charset.forName("GBK")))
                .writeShort(tcpPort)
                .writeShort(udpPort)
                .writeBytes(alarmIdentification.getTerminalId().getBytes(Charset.forName("GBK")))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(date, "yy-MM-dd-HH-mm-ss")))
                .writeByte(alarmIdentification.getSerialNumber())
                .writeByte(alarmIdentification.getNumberOfAttachments())
                .writeByte(alarmIdentification.getReserve())
                .writeBytes(alarmNo.getBytes(Charset.forName("GBK")))
                .writeBytes(reserve).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
