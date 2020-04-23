package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.AdditionalAble;
import com.avenger.jt808.enums.RegionType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
@AdditionalAble(type = 0x11)
public class OverSpeedAdd implements Additional {

    private RegionType regionType;

    private Integer roundId;

    @Override
    public byte[] serialize() {

        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.regionType = RegionType.valueOf(byteBuf.readByte());
        if (regionType == RegionType.NO_SPECIFIC_LOCATION) return;
        this.roundId = byteBuf.readInt();
    }
}
