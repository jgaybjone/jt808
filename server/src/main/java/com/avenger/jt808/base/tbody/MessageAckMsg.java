package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8203))
@ToString
public class MessageAckMsg implements Body {

    /**
     * 报警消息流水号
     */
    @Getter
    @Setter
    private short respSerialNo;

    /**
     * 人工确认报警类型
     */
    private int ackType = 0;

    /**
     * 设计未紧急报警
     */
    public void setEmergency() {
        ackType = (ackType | 0b1);
    }

    /**
     * 确认危险预警
     */
    public void setDanger() {
        ackType = (ackType | 0b1000);
    }

    /**
     * 确认进出区域报警;
     */
    public void setInOrOut() {
        ackType = (ackType | 0b100000000000000000000);
    }

    /**
     * 确认进出路线报警;
     */
    public void setDerailment() {
        ackType = (ackType | 0b1000000000000000000000);
    }

    /**
     * 确认路段行驶时间不足/过长报警;
     */
    public void setDrivingTime() {
        ackType = (ackType | 0b10000000000000000000000);
    }

    /**
     * 确认车辆非法点火报警;
     */
    public void setIllegalIgnition() {
        ackType = (ackType | 0b1000000000000000000000000000);
    }

    /**
     * 确认车辆非法位移报警;
     */
    public void setIllegalMovement() {
        ackType = (ackType | 0b10000000000000000000000000000);
    }

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(6).writeShort(respSerialNo).writeInt(ackType).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        byteBuf.writeShort(respSerialNo).writeInt(this.ackType);
    }
}
