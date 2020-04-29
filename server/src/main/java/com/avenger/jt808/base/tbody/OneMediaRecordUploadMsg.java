package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 单条存储多媒体数据检索上传
 */
@WritingMessageType(type = (0x8805 - 0x10000))
@Data
public class OneMediaRecordUploadMsg implements Body {

    private int id;

    private boolean delete;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(5)
                .writeInt(id)
                .writeByte(delete ? 0 : 1));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
