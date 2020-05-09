package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((short) 0x8300), needReply = true)
@ToString
public class PushMsg implements Body {

    private byte flag = 0;

    @Getter
    @Setter
    private byte type;

    @Getter
    @Setter
    private String message;

    /**
     * 紧急消息
     */
    public void setTextType(TextType textType) {
        flag = ((byte) (flag & ~0b11));
        flag = (byte) (flag | textType.getValue());
    }

    /**
     * 终端显示器显示
     */
    public void setLcd(boolean b) {
        if (b) {
            flag = (byte) (flag | 0b100);
        } else {
            flag = (byte) (flag & (~0b100));
        }
    }

    /**
     * 终端 TTS 播读
     */
    public void setTts(boolean b) {
        if (b) {
            flag = (byte) (flag | 0b1000);
        } else {
            flag = (byte) (flag & (~0b1000));
        }
    }

    /**
     * 广告屏显示
     */

    public void setAd(boolean b) {
        if (b) {
            flag = (byte) (flag | 0b10000);
        } else {
            flag = (byte) (flag & (~0b10000));
        }
    }

    /**
     * 1:CAN 故障码信息
     */
    public void setCan(boolean b) {
        if (b) {
            flag = (byte) (flag | 0b100000);
        } else {
            flag = (byte) (flag & (~0b100000));
        }
    }

    /**
     * 0:中心导航信息
     */
    public void setCenterNavigation(boolean b) {
        if (b) {
            flag = (byte) (flag | 0b11011111);
        } else {
            flag = (byte) (flag & (~0b11011111));
        }
    }

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(200)
                .writeByte(flag)
                .writeByte(type)
                .writeBytes(message.getBytes(Charset.forName("GBK"))));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum TextType {
        SERVICE(((byte) 0b01)),
        EMERGENCY(((byte) 0b10)),
        NOTIFICATION(((byte) 0b11));

        @Getter
        private final byte value;

        TextType(byte b) {
            this.value = b;
        }
    }
}
