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
@Data
@AdditionalAble(type = 0x01)
public class MileageAdd implements Additional {

    private int mileage;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(5)
                .writeByte(getId())
                .writeInt(mileage));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.mileage = byteBuf.readInt();
    }
}
