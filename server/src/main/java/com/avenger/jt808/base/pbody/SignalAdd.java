package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@Data
@AdditionalAble(type = 0x30)
public class SignalAdd implements Additional {

    private byte value;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(2).writeByte(getId()).writeByte(value));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.value = byteBuf.readByte();
    }
}
