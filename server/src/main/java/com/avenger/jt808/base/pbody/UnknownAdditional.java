package com.avenger.jt808.base.pbody;

import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/10.
 * Description:
 */
@Data
public class UnknownAdditional implements Additional {

    private byte id;

    private byte[] raw;

    @Override
    public byte[] serialize() {
        return raw;
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.raw = ByteBufUtils.array(byteBuf);
    }
}
