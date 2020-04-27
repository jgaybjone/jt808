package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/19.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x9207))
@Data
public class UploadControlMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    private short respSerialNo;

    private Cmd cmd;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return ByteBufUtils.array(Unpooled.buffer(3)
                .writeShort(respSerialNo)
                .writeByte(cmd.ordinal()));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum Cmd {
        /**
         * 暂停
         */
        PAUSE,
        /**
         * 继续
         */
        CONTINUE,
        /**
         * 取消
         */
        CANCEL
    }
}
