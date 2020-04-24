package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8300))
@ToString
public class PushMsg implements Body {

    private byte flag = 0;

    @Getter
    @Setter
    private String message;

    /**
     * 紧急消息
     */
    public void setEmergency() {
        flag = (byte) (flag | 0b1);
    }

    /**
     * 终端显示器显示
     */
    public void setLcd() {
        flag = (byte) (flag | 0b100);
    }

    /**
     * 终端 TTS 播读
     */
    public void setTts() {
        flag = (byte) (flag | 0b1000);
    }

    /**
     * 广告屏显示
     */

    public void setAd() {
        flag = (byte) (flag | 0b10000);
    }

    /**
     * 1:CAN 故障码信息
     */
    public void setCan() {
        flag = (byte) (flag | 0b100000);
    }

    /**
     * 0:中心导航信息
     */
    public void setCenterNavigation() {
        flag = (byte) (flag | 0b11101111);
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
