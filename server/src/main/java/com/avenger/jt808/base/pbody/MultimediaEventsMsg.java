package com.avenger.jt808.base.pbody;

import com.avenger.jt808.domain.ReadingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.enums.EventItem;
import com.avenger.jt808.enums.MediaFormat;
import com.avenger.jt808.enums.MultimediaEventType;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.io.UnsupportedEncodingException;

/**
 * Created by jg.wang on 2020/4/13.
 * Description:
 */
@ReadingMessageType(type = 0x0800)
@Data
public class MultimediaEventsMsg implements Body {
    /**
     * 多媒体数据ID
     */
    private int id;

    /**
     * 0:图像;1:音频;2:视频;
     */
    private MultimediaEventType type;

    /**
     * 0:JPEG;1:TIF;2:MP3;3:WAV;4:WMV; 其他保留
     */
    private MediaFormat format;

    /**
     * 0:平台下发指令;
     * 1:定时动作;
     * 2:抢劫报警触 发;
     * 3:碰撞侧翻报警触发;
     * 4:门开拍照;
     * 5:门 关拍照;
     * 6:车门由开变关，时速从<20 公里到超 过 20 公里;
     * 7:定距拍照;
     * 其他保留
     */
    private EventItem eventItem;
    /**
     * 通道ID
     */
    private byte channelId;


    @Override
    public byte[] serialize() throws UnsupportedEncodingException {
        return new byte[0];
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {
        this.id = byteBuf.readInt();
        this.type = MultimediaEventType.valueOf(byteBuf.readByte());
        this.format = MediaFormat.valueOf(byteBuf.readByte());
        this.eventItem = EventItem.valueOf(byteBuf.readByte());
        this.channelId = byteBuf.readByte();
    }
}
