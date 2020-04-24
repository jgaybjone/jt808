package com.avenger.jt808;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/24.
 * Description: 文件上传完成消息
 */
@ReadingMessageType(type = 0x1212)
@Data
public class UploadCompleteMsg implements Body {
    private String fileName;

    private AlarmFileType fileType;

    private int size;

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf myBuffer) {
        this.fileName = ByteBufUtils.toStringWithGBK(myBuffer, myBuffer.readByte());
        this.fileType = AlarmFileType.create(myBuffer.readByte());
        this.size = myBuffer.readInt();
    }
}
