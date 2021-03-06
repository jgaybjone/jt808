package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@ReadingMessageType(type = 0x0801)
@Data
public class MultimediaData extends MultimediaEventsMsg {

    private LocationAndAlarmMsg locationAndAlarmMsg;

    private byte[] data;

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        super.deSerialize(byteBuf);
        final ByteBuf b = byteBuf.readBytes(28);
        final LocationAndAlarmMsg l = new LocationAndAlarmMsg();
        l.deSerialize(b);
        this.locationAndAlarmMsg = l;
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        this.data = bytes;

    }

    @Override
    public void deSerializeSubpackage(ByteBuf byteBuf) {
        final byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        this.data = bytes;
    }
}
