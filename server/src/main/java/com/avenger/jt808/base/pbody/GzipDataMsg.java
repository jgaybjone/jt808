package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@ReadingMessageType(type = 0x0901)
@Data
public class GzipDataMsg implements Body {

    private byte[] data;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        final byte[] bytes = new byte[byteBuf.readInt()];
        byteBuf.readBytes(bytes);
        this.data = bytes;
    }
}
