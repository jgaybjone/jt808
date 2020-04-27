package com.avenger.jt808;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import com.avenger.jt808.util.CommonResult;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 平台通用应答0x8001和终端通用应答0x0001
 */
@Data
@ReadingMessageType(type = 0x0001)
@WritingMessageType(type = (short) 0x8001)
public class CommonMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    private short respSerialNo;

    /**
     * 对应的平台消息的 ID
     */
    private short respId;

    private CommonResult result;

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(5);
        buffer.writeShort(respSerialNo);
        buffer.writeShort(respId);
        buffer.writeByte(result.getValue());
        return ByteBufUtils.array(buffer);
    }

    @Override
    public void deSerialize(ByteBuf buffer) {
        this.respSerialNo = buffer.readShort();
        this.respId = buffer.readShort();
        this.result = CommonResult.valueOf(buffer.readByte());
    }
}
