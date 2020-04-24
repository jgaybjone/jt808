package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:
 */
@ReadingMessageType(type = 0x0301)
@Data
public class EventReportMsg implements Body {

    private byte eventId;

    @Override
    public byte[] serialize() {

        return new byte[]{eventId};
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.eventId = byteBuf.readByte();
    }
}
