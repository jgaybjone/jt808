package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@ReadingMessageType(type = (0x9003 - 0x10000))
public class QueryAudioAndVideoAttrMsg implements Body {
    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
