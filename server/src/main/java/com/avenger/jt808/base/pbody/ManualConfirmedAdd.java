package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description: 需要人工确认的报警
 */
@Data
@AdditionalAble(type = 0x04)
public class ManualConfirmedAdd implements Additional {

    private short recordId;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(3)
                .writeByte(getId())
                .writeShort(recordId));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        recordId = byteBuf.readShort();
    }
}
