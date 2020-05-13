package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.enums.EventItem;
import com.avenger.jt808.enums.MultimediaEventType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by jg.wang on 2020/4/13.
 * Description: 存储多媒体数据上传
 */
@WritingMessageType(type = (0x8803 - 0x10000))
@Data
public class MultimediaDataRecordUploadMsg implements Body {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    /**
     * 多媒体类型
     */
    private MultimediaEventType type;
    /**
     * 通道 ID
     */
    private int channelId;
    /**
     * 事件项编码
     * 0:平台下发指令;
     * 1:定时动作;
     * 2:抢劫报警触 发;
     * 3:碰撞侧翻报警触发;其他保留
     */
    private EventItem eventItem;
    /**
     * 起始时间 YY-MM-DD-hh-mm-ss
     */
    private LocalDate startDate;

    /**
     * 结束时间 YY-MM-DD-hh-mm-ss
     */
    private LocalDate endDate;
    /**
     * 删除标志
     */
    private boolean delete;

    @Override
    public byte[] serialize() {
        return ByteBufUtils.array(Unpooled.buffer(16)
                .writeByte(type.ordinal())
                .writeByte(channelId)
                .writeByte(eventItem.ordinal())
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DF.format(startDate)))
                .writeBytes(ByteArrayUtils.bcdStrToBytes(DF.format(endDate)))
                .writeByte(delete ? 1 : 0));
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }
}
