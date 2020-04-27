package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

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
     * 重传包总数
     */
    private byte total;

    /**
     * 重传包 ID 列表
     */
    private byte[] packetIds;

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(10);
        return ByteBufUtils.array(buffer.writeShort(originalSerialNo)
                .writeByte(total)
                .writeBytes(packetIds));
    }

    @Override
    public void deSerialize(ByteBuf buffer) {
        this.originalSerialNo = buffer.readShort();
        this.total = buffer.readByte();
        this.packetIds = new byte[total];
        buffer.readBytes(this.packetIds);
    }


}
