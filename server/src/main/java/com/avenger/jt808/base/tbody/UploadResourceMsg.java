package com.avenger.jt808.base.tbody;

import com.avenger.jt808.base.annotation.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.base.pbody.AlarmDetail;
import com.avenger.jt808.enums.ResourceType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.LocalDateTimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/19.
 * Description: FTP文件上传
 */
@WritingMessageType(type = ((byte) 0x9206))
@Data
public class UploadResourceMsg implements Body {

    private String ip;

    private short port;

    private String username;

    private String password;

    private String uploadPath;

    private byte channelId;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private AlarmDetail alarmDetail;

    private int alarmExt;

    private ResourceType resourceType;

    private QueryResourceListMsg.BitStreamType bitStreamType;

    private QueryResourceListMsg.MemType memType;

    private byte networkConditions;

    public void setCondition(boolean wifi, boolean lan, boolean cel) {
        if (wifi) {
            this.networkConditions = (byte) (networkConditions | 0b1);
        } else {
            this.networkConditions = (byte) (networkConditions & 0xFE);
        }
        if (lan) {
            this.networkConditions = (byte) (networkConditions | 0b10);
        } else {
            this.networkConditions = (byte) (networkConditions & (~0b10));
        }
        if (cel) {
            this.networkConditions = (byte) (networkConditions | 0b100);
        } else {
            this.networkConditions = (byte) (networkConditions & (~0b100));
        }
    }


    @Override
    public byte[] serialize() {
        return Unpooled.buffer(100)
                .writeByte(ip.length())
                .writeBytes(ip.getBytes(Charset.forName("GBK")))
                .writeByte(port)
                .writeByte(username.length())
                .writeBytes(username.getBytes(Charset.forName("GBK")))
                .writeByte(password.length())
                .writeBytes(password.getBytes(Charset.forName("GBK")))
                .writeByte(uploadPath.length())
                .writeBytes(uploadPath.getBytes(Charset.forName("GBK")))
                .writeByte(channelId)
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(startAt, "yy-MM-dd-HH-mm-ss")))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(endAt, "yy-MM-dd-HH-mm-ss")))
                .writeInt(alarmDetail.getAlarmFlag())
                .writeInt(alarmExt)
                .writeByte(resourceType.ordinal())
                .writeByte(bitStreamType.ordinal())
                .writeByte(memType.ordinal())
                .writeByte(networkConditions)
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
