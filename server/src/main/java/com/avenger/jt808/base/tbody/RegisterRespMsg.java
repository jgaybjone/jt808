package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.RegisterResult;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 终端注册应答消息
 */
@WritingMessageType(type = (short) 0x8100)
@Data
public class RegisterRespMsg implements Body {

    /**
     * 应答流水号
     */
    private short respSerial;

    /**
     * 结果
     */
    private RegisterResult result;

    /**
     * 鉴权码
     */
    private String authCode;

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(10);
        buffer.writeShort(respSerial);
        buffer.writeByte(result.getValue());
        if (result == RegisterResult.OK) {
            buffer.writeBytes(authCode.getBytes(Charset.forName("GBK")));
        }
        return ByteBufUtils.array(buffer);
    }

    @Override
    public void deSerialize(ByteBuf buffer) {
        this.respSerial = buffer.readShort();
        this.result = RegisterResult.valueOf(buffer.readByte());
        final int remaining = buffer.readableBytes();
        this.authCode = ByteBufUtils.toStringWithGBK(buffer, remaining);
    }
}
