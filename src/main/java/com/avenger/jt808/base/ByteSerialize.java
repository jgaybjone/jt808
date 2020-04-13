package com.avenger.jt808.base;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
public interface ByteSerialize {

    byte[] serialize() throws UnsupportedEncodingException;

    void deSerialize(ByteBuf byteBuf);

}
