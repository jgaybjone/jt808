package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.AdditionalAble;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@Data
@AdditionalAble(type = 0x2A)
public class IoStatusAdd implements Additional {

    private short status;

    public boolean isDeepSleep() {
        return (status & 0b0000000000000001) > 0;
    }

    public boolean isSleep() {
        return (status & 0b10) > 0;
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
