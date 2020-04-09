package com.avenger.jt808.base;

import io.netty.buffer.ByteBuf;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
public interface ByteSerialize {

    byte[] serialize();

    void deSerialize(ByteBuf byteBuf);

}
