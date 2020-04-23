package com.avenger.jt808.base.pbody;

import com.avenger.jt808.base.annotation.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/19.
 * Description:
 */
@ReadingMessageType(type = 0x1206)
@Data
public class UploadFinishMsg implements Body {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    private short respSerialNo;

    private boolean success;

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.respSerialNo = byteBuf.readShort();
        this.success = byteBuf.readByte() == 0;
    }
}
