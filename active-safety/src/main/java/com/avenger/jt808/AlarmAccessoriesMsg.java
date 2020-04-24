package com.avenger.jt808;

import com.avenger.jt808.domain.AlarmIdentification;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jg.wang on 2020/4/24.
 * Description: 报警附件信息消息
 */
@ReadingMessageType(type = 0x1210)
@Data
public class AlarmAccessoriesMsg implements Body {

    private String terminalId;

    private AlarmIdentification alarmIdentification;

    private String alarmNo;

    private byte type;

    private List<AttachmentInfo> attachmentInfos;

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf buffer) {

        this.terminalId = ByteBufUtils.toStringWithGBK(buffer, 7);
        this.alarmIdentification = new AlarmIdentification();
        this.alarmIdentification.setTerminalId(ByteBufUtils.toStringWithGBK(buffer, 7));
        this.alarmIdentification.setDate(ByteArrayUtils.bcdToDate(buffer, 6));
        this.alarmIdentification.setSerialNumber(buffer.readByte());
        this.alarmIdentification.setNumberOfAttachments(buffer.readByte());
        this.alarmIdentification.setReserve(buffer.readByte());
        this.alarmNo = ByteBufUtils.toStringWithGBK(buffer, 23);
        this.type = buffer.readByte();
        final byte numberOfAttachments = buffer.readByte();
        final ArrayList<AttachmentInfo> attachmentInfos = new ArrayList<>();
        for (int i = 0; i < numberOfAttachments && buffer.isReadable(); i++) {
            final AttachmentInfo attachmentInfo = new AttachmentInfo();
            attachmentInfo.setFileName(ByteBufUtils.toStringWithGBK(buffer, buffer.readByte()));
            attachmentInfo.setSize(buffer.readInt());
            attachmentInfos.add(attachmentInfo);
        }
        this.attachmentInfos = attachmentInfos;

    }
}
