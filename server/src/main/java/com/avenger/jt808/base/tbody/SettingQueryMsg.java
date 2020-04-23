package com.avenger.jt808.base.tbody;

import com.avenger.jt808.base.annotation.WritingMessageType;
import com.avenger.jt808.domain.Body;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 查询终端参数
 */
@WritingMessageType(type = (byte) 0x8106)
@Data
public class SettingQueryMsg implements Body {

    private List<Integer> ids;

    @Override
    public byte[] serialize() {
        if (CollectionUtils.isEmpty(ids)) {
            return new byte[0];
        }
        final ByteBuf buffer = Unpooled.buffer(ids.size() * 4);
        ids.forEach(buffer::writeInt);
        return buffer.array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        final byte size = byteBuf.readByte();
        final List<Integer> l = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            l.add(byteBuf.readInt());
        }
        this.ids = l;
    }
}
