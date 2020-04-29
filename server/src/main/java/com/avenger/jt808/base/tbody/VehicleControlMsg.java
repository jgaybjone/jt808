package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = (0x8500 - 0x10000))
@AllArgsConstructor
public class VehicleControlMsg implements Body {

    private byte flag;

    public void lockDoor() {
        flag = (byte) (flag | 0b1);
    }

    public void unlockDoor() {
        flag = (byte) (flag & 0xFE);
    }


    @Override
    public byte[] serialize() {
        return new byte[]{flag};
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
