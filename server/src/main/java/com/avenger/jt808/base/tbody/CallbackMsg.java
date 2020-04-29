package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/11.
 * Description: 电话回拨消息
 */
@ToString
@WritingMessageType(type = (0x8400 - 0x10000))
public class CallbackMsg implements Body {

    private byte flag = 0;

    public void monitor() {
        this.flag = 1;
    }

    @Getter
    @Setter
    private String simNo;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(100)
                .writeByte(flag)
                .writeByte(simNo.length())
                .writeBytes(simNo.getBytes(Charset.forName("GBK"))));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
