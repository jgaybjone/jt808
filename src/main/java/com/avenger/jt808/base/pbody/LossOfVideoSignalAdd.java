package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.AdditionalAble;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/17.
 * Description: 丢失视频信号报警
 */
@AdditionalAble(type = 0x15)
public class LossOfVideoSignalAdd implements Additional {

    private int raw;

    public List<Integer> getChannelId() {
        final List<Integer> channelIds = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
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
        this.raw = byteBuf.readInt();
    }
}
