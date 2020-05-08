package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/9.
 * Description: 补传分包请求消息体数据格式
 */
@WritingMessageType(type = (short) 0x8003)
@Data
public class ReissueMsg implements Body {

    /**
     * 原始消息流水号
     */
    private short originalSerialNo;

    /**
     * 重传包 ID 列表
     */
    private List<Short> packetIds = new ArrayList<>();

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(10);
        buffer.writeShort(originalSerialNo)
                .writeByte(packetIds.size());
        packetIds.forEach(buffer::writeShort);
        return ByteBufUtils.array(buffer);
    }

    @Override
    public void deSerialize(ByteBuf buffer) {
        this.originalSerialNo = buffer.readShort();
        byte total = buffer.readByte();
        for (int i = 0; i < total; i++) {
            packetIds.add(buffer.readShort());
        }
    }


}
