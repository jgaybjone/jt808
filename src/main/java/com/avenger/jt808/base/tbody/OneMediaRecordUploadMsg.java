package com.avenger.jt808.base.tbody;

import com.avenger.jt808.annotation.WritingMessageType;
import com.avenger.jt808.base.Body;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 单条存储多媒体数据检索上传
 */
@WritingMessageType(type = ((byte) 0x8805))
@Data
public class OneMediaRecordUploadMsg implements Body {

    private int id;

    private boolean delete;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(5)
                .writeInt(id)
                .writeByte(delete ? 0 : 1)
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
