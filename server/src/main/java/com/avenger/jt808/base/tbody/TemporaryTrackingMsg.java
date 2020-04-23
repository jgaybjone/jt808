package com.avenger.jt808.base.tbody;

import com.avenger.jt808.base.annotation.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@Data
@WritingMessageType(type = ((short) 0x8202))
public class TemporaryTrackingMsg implements Body {

    private short timeInterval;

    private int duration;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(6).writeShort(timeInterval).writeInt(duration).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.timeInterval = byteBuf.readShort();
        this.duration = byteBuf.readInt();
    }
}
