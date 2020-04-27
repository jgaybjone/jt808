package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@ReadingMessageType(type = 0x0302)
@Data
public class AnswerMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    private short respSerialNo;

    private byte answerId;


    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(3).writeShort(respSerialNo).writeByte(answerId));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.respSerialNo = byteBuf.readShort();
        this.answerId = byteBuf.readByte();
    }
}
