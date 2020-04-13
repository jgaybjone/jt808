package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.AdditionalAble;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
@AdditionalAble(type = 0x02)
public class FuelQuantityAdd implements Additional {

    private short fuelQuantity;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(3)
                .writeByte(getId())
                .writeShort(fuelQuantity)
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        fuelQuantity = byteBuf.readShort();
    }

}
