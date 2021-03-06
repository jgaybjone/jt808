package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 终端鉴权消息
 */
@Data
@ReadingMessageType(type = 0x0102)
public class AuthMsg implements Body {

    /**
     * 鉴权码
     */
    private String authCode;

    @Override
    public byte[] serialize() {
        return authCode.getBytes(Charset.forName("GBK"));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.authCode = new String(ByteBufUtils.array(byteBuf), Charset.forName("GBK"));
    }
}
