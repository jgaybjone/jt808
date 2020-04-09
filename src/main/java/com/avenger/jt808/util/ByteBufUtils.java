package com.avenger.jt808.util;

import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@UtilityClass
public class ByteBufUtils {

    public static String toStringWithGBK(ByteBuf  buffer, int len) {
        final byte[] l = new byte[len];
        buffer.readBytes(l);
        return new String(l, Charset.forName("GBK"));
    }
}
