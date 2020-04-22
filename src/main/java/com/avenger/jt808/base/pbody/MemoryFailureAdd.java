package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.AdditionalAble;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/17.
 * Description:
 */
@AdditionalAble(type = 0x17)
public class MemoryFailureAdd implements Additional {
    private short raw;

    /**
     * 1~12 主存储器
     * 13~16 灾备存储器
     *
     * @return ids
     */
    public List<Integer> getMemoryId() {
        final List<Integer> channelIds = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (((raw >> i) & 0b1) > 0) {
                channelIds.add(i + 1);
            }
        }
        return channelIds;
    }

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.raw = byteBuf.readShort();
    }
}
