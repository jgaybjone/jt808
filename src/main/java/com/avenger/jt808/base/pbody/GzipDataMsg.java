package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.ReadingMessageType;
import com.avenger.jt808.base.Body;
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
        this.data = byteBuf.readBytes(byteBuf.readInt()).array();
    }
}