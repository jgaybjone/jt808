package com.avenger.jt808.base.pbody;

import com.avenger.jt808.annotation.ReadingMessageType;
import com.avenger.jt808.base.Body;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/12.
 * Description: 电子运单上报消息
 */
@ReadingMessageType(type = 0x0701)
public class WaybillReportMsg implements Body {

    @Setter
    @Getter
    private String content;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(content.length() + 4).writeInt(content.length())
                .writeBytes(content.getBytes(Charset.forName("GBK"))).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        final int len = byteBuf.readInt();
        this.content = ByteBufUtils.toStringWithGBK(byteBuf, len);

    }
}
