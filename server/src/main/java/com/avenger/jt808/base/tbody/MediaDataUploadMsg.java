package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 多媒体数据上传应答
 */
@WritingMessageType(type = ((byte) 0x8800))
@Data
public class MediaDataUploadMsg implements Body {
    /**
     * 多媒体 ID
     */
    private int id;

    private List<Short> packageNos = new ArrayList<>();


    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        final ByteBuf byteBuf = Unpooled.buffer(packageNos.size() * 2 + 5)
                .writeInt(id)
                .writeByte(packageNos.size());
        packageNos.forEach(byteBuf::writeShort);
        return ByteBufUtils.array(byteBuf);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
