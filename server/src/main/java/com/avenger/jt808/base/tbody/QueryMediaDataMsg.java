package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.EventItem;
import com.avenger.jt808.enums.MultimediaEventType;
import com.avenger.jt808.util.ByteArrayUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x8802))
@Data
public class QueryMediaDataMsg implements Body {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yy-MM-dd-HH-mm-ss");

    /**
     * 0:图像;1:音频;2:视频;
     */
    private MultimediaEventType type;

    /**
     * 0 表示检索该媒体类型的所有通道;
     */
    private byte channelId;

    private EventItem eventItem;

    private LocalDate startDate;

    private LocalDate endDate;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(15)
                .writeByte(type.ordinal())
                .writeByte(channelId)
                .writeByte(eventItem.ordinal())
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DF.format(startDate)))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DF.format(endDate))).array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
