package com.avenger.jt808;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by jg.wang on 2020/4/24.
 * Description: 文件信息上传
 */
@ReadingMessageType(type = 0x1211)
@Data
public class AttachmentInformationMsg implements Body {

    private String fileName;

    private AlarmFileType alarmFileType;

    private int size;

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf buffer) {
        this.fileName = ByteBufUtils.toStringWithGBK(buffer, buffer.readByte());
        this.alarmFileType = AlarmFileType.create(buffer.readByte());
        this.size = buffer.readByte();
    }
}
