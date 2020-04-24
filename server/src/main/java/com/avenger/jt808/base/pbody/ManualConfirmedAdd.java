package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
@AdditionalAble(type = 0x04)
public class ManualConfirmedAdd implements Additional {

    private short recordId;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(3)
                .writeByte(getId())
                .writeShort(recordId)
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        recordId = byteBuf.readShort();
    }
}
