package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jg.wang on 2020/4/11.
 * Description: 车辆控制应答消息体
 */
@ReadingMessageType(type = 0x0500)
public class VehicleControlRespMsg extends LocationAndAlarmMsg {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    @Getter
    @Setter
    private short respSerialNo;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(500)
                .writeShort(respSerialNo)
                .writeBytes(super.serialize())
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.respSerialNo = byteBuf.readShort();
        super.deSerialize(byteBuf);
    }
}
