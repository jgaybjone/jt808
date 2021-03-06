package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.enums.EventItem;
import com.avenger.jt808.enums.MultimediaEventType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@WritingMessageType(type = ((short) 0x8802), needReply = true)
@Data
public class QueryMediaDataMsg implements Body {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    /**
     * 0:图像;1:音频;2:视频;
     */
    private MultimediaEventType type;

    /**
     * 0 表示检索该媒体类型的所有通道;
     */
    private byte channelId;

    private EventItem eventItem;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(15)
                .writeByte(type.ordinal())
                .writeByte(channelId)
                .writeByte(eventItem.ordinal())
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DF.format(startDate)))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DF.format(endDate))));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
