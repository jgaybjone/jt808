package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@AdditionalAble(type = 0x2B)
@Data
public class AnalogAdd implements Additional {

    private int date;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(5).writeByte(getId()).writeInt(date).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.date = byteBuf.readInt();
    }
}
