package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@ReadingMessageType(type = ((byte) 0x9003))
public class QueryAudioAndVideoAttrMsg implements Body {
    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
