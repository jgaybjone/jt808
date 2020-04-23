package com.avenger.jt808.base.tbody;

import com.avenger.jt808.base.annotation.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8A00))
@Data
public class PlatformRasMsg implements Body {
    /**
     * 平台 RSA 公钥{e,n}中的 e
     */
    private int e;

    private String n;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return Unpooled.buffer(132).writeInt(e).writeBytes(n.getBytes(Charset.forName("GBK"))).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
