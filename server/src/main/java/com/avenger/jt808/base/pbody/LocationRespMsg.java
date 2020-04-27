package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@ReadingMessageType(type = 0x0201)
public class LocationRespMsg extends LocationAndAlarmMsg {

    /**
     * 应答流水号，对应终端下发时的流水号
     */
    @Getter
    @Setter
    private short respSerialNo;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(200).writeShort(respSerialNo).writeBytes(super.serialize()));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.respSerialNo = byteBuf.readShort();
        super.deSerialize(byteBuf);
    }
}
