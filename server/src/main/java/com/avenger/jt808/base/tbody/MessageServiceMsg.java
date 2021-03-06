package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@WritingMessageType(type = (0x8304 - 0x10000))
@Data
public class MessageServiceMsg implements Body {

    /**
     * 信息类型
     */
    private byte type;

    /**
     * 信息内容
     */
    private String content;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(200)
                .writeByte(type)
                .writeByte(content.length())
                .writeBytes(content.getBytes(Charset.forName("GBK"))));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
