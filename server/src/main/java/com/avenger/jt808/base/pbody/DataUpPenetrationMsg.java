package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:数据上行透传
 */
@ReadingMessageType(type = ((byte) 0x0900))
@Data
public class DataUpPenetrationMsg implements Body {
    /**
     * 0x00
     */
    private byte type;

    private byte[] data;


    @Override
    public byte[] serialize() {

        return ByteBufUtils.array(Unpooled.buffer(data.length + 1)
                .writeByte(type)
                .writeBytes(data));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
