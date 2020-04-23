package com.avenger.jt808.base.tbody;

import com.avenger.jt808.base.annotation.WritingMessageType;
import com.avenger.jt808.domain.Body;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.LocalDateTimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/19.
 * Description:
 */
@WritingMessageType(type = ((byte) 0x9202))
@Data
public class VideoRelayControlMsg implements Body {

    private byte channelId;

    private RelayControl relayControl;
    /**
     * 开进快退倍数
     */
    private byte multiple;

    private LocalDateTime time;

    @Override
    public byte[] serialize() {
        return Unpooled.buffer(9)
                .writeByte(channelId)
                .writeByte(relayControl.ordinal())
                .writeByte(multiple)
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(time, "yy-MM-dd-HH-mm-ss")))
                .array();
    }

    @Override
    public void deSerialize(ByteBuf byteBuf) {

    }

    public enum RelayControl {
        /**
         * 开始回放
         */
        START_PLAYBACK,
        /**
         * 暂停
         */
        SUSPEND,
        /**
         * 结束
         */
        FINISH,
        /**
         * 快进
         */
        FAST_FORWARD,
        /**
         * 关键字快退回放
         */
        KEYWORD_REWIND,
        /**
         * 拖动回放
         */
        DRAG_PLAYBACK,
        /**
         * 关键帧播放
         */
        KEY_PLAYBACK,
    }
}
