package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 终端RSA公钥
 */
@ReadingMessageType(type = 0x0A00)
@Data
public class TermRsaMsg implements Body {

    /**
     * 终端 RSA 公钥{e,n}中的 e
     */
    private int e;

    private String n;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
