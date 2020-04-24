package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/11.
 * Description: 删除路线消息
 */
@WritingMessageType(type = ((byte) 0x8607))
@Data
public class DeleteRouteMsg implements Body {

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
