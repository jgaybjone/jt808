package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.enums.BitstreamType;
import com.avenger.jt808.enums.CloseType;
import com.avenger.jt808.enums.RealTimeControlCmd;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/18.
 * Description:
 */
@WritingMessageType(type = (0x9102 - 0x10000))
@Data
public class RealTimeTransmissionControlMsg implements Body {

    private RealTimeControlCmd cmd;

    private CloseType closeType;

    private BitstreamType bitstreamType;

    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return ByteBufUtils.array(Unpooled.buffer(3)
                .writeByte(cmd.ordinal())
                .writeByte(closeType.ordinal())
                .writeByte(bitstreamType.ordinal()));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
