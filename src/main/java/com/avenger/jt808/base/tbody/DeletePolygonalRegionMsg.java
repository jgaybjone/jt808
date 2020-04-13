package com.avenger.jt808.base.tbody;

import com.avenger.jt808.annotation.WritingMessageType;
import com.avenger.jt808.base.Body;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description:m删除多边形区域
 */
@WritingMessageType(type = ((byte) 0x8605))
@Data
public class DeletePolygonalRegionMsg implements Body {

    private List<Integer> ids = Collections.emptyList();

    @Override
    public byte[] serialize() {
        final ByteBuf byteBuf = Unpooled.buffer(ids.size() + 1)
                .writeByte(ids.size());
        ids.forEach(byteBuf::writeInt);
        return byteBuf.array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
