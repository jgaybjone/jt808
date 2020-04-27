package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@AdditionalAble(type = 0x03)
@Data
public class TachographSpeedAdd implements Additional {

    private short speed;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(3)
                .writeByte(getId())
                .writeShort(speed));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        speed = byteBuf.readShort();
    }
}
