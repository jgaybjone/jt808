package com.avenger.jt808;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/24.
 * Description: 文件上传完成消息应答
 */
@WritingMessageType(type = ((byte) 0x9212))
@Data
public class UploadCompleteRespMsg implements Body {

    private String fileName;

    private AlarmFileType fileType;

    private byte finished;

    private List<MissingPacket> missingPackets = Collections.emptyList();

    @Override
    public byte[] serialize() {
        final ByteBuf buffer = Unpooled.buffer(20);
        buffer.writeBytes(new byte[]{(byte) fileName.length()});
        buffer.writeBytes(fileName.getBytes(Charset.forName("GBK")));
        buffer.writeByte(finished);
        if (missingPackets != null && !missingPackets.isEmpty()) {
            buffer.writeByte(missingPackets.size());
            for (MissingPacket missingPacket : missingPackets) {
                buffer.writeInt(missingPacket.getOffset());
                buffer.writeInt(missingPacket.getLength());
            }
        } else {
            buffer.writeByte(0);
        }

        return ByteBufUtils.array(buffer);
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    @Data
    public static class MissingPacket {
        private int offset;
        private int length;
    }
}
