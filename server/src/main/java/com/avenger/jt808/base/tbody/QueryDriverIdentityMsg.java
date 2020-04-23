package com.avenger.jt808.base.tbody;

import com.avenger.jt808.base.annotation.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/12.
 * Description: 上报驾驶员身份信息请求
 */
@WritingMessageType(type = ((byte) 0x8702))
public class QueryDriverIdentityMsg implements Body {

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
