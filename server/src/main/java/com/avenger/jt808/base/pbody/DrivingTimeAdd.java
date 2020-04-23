package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.AdditionalAble;
import com.avenger.jt808.enums.DrivingTimeResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description: 路段行驶时间不足/过长报警附加信息
 */
@Data
@AdditionalAble(type = 0x13)
public class DrivingTimeAdd implements Additional {

    private int roundId;

    private short drivingTime;

    private DrivingTimeResult result;


    @Override
    public byte[] serialize() {
        return Unpooled.buffer(7)
                .writeByte(getId())
                .writeInt(roundId)
                .writeShort(drivingTime)
                .writeByte(result.getValue())
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.roundId = byteBuf.readInt();
        this.drivingTime = byteBuf.readShort();
        this.result = DrivingTimeResult.valueOf(byteBuf.readByte());
    }
}
