package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import com.avenger.jt808.enums.RegionType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
@AdditionalAble(type = 0x12)
public class DerailmentAdd implements Additional {

    private RegionType regionType;

    private int roundId;

    private boolean enter;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(6)
                .writeByte(getId())
                .writeByte(regionType.getValue())
                .writeInt(roundId)
                .writeByte(enter ? 0x00 : 0x01)
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.regionType = RegionType.valueOf(byteBuf.readByte());
        this.roundId = byteBuf.readInt();
        this.enter = byteBuf.readByte() == 0x00;
    }
}
