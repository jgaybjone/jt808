package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.AdditionalAble;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@AdditionalAble(type = 0x25)
@Data
public class StatusExtendAdd implements Additional {

    private int status;

    /**
     * 打开近光灯
     */
    public boolean isDippedBeamHeadlight() {
        return (status & 0b1) > 0;
    }

    /**
     * 打开远光灯光灯
     */
    public boolean isHighBeam() {
        return (status & 0b10) > 0;
    }

    /**
     * 打开右转信号灯
     */
    public boolean isRightTurnLamp() {
        return (status & 0b100) > 0;
    }

    /**
     * 打开左转信号灯
     */
    public boolean isLeftTurnLamp() {
        return (status & 0b1000) > 0;
    }

    /**
     * 刹车信号
     */
    public boolean isBrakeSignal() {
        return (status & 0b10000) > 0;
    }

    /**
     * 倒车信号
     */
    private boolean isReversingSignal() {
        return (status & 0b100000) > 0;
    }

    /**
     * 雾灯信号
     */
    private boolean isFogLamp() {
        return (status & 0b1000000) > 0;
    }

    /**
     * 示廓灯
     */
    private boolean isOutlineLamp() {
        return (status & 0b10000000) > 0;
    }

    /**
     * 鸣笛
     */
    private boolean isWhistle() {
        return (status & 0b100000000) > 0;
    }

    /**
     * 空调开
     */
    private boolean isAirConditioner() {
        return (status & 0b1000000000) > 0;
    }

    /**
     * 空挡信号
     */
    private boolean isNeutralSignal() {
        return (status & 0b10000000000) > 0;
    }

    /**
     * 缓速器工作
     */
    private boolean isRetarderOperation() {
        return (status & 0b100000000000) > 0;
    }

    /**
     * ABS工作
     */
    private boolean isABSWork() {
        return (status & 0b1000000000000) > 0;
    }

    /**
     * 加热器工作
     */
    private boolean isHeaterOperation() {
        return (status & 0b10000000000000) > 0;
    }

    /**
     * 离合器状态
     */
    private boolean isClutchStatus() {
        return (status & 0b10000000000000) > 0;
    }



    @Override
    public byte[] serialize() {
        return Unpooled.buffer(5)
                .writeByte(getId())
                .writeByte(status).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.status = byteBuf.readInt();
    }
}
