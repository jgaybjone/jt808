package com.avenger.jt808.base.tbody;

import com.avenger.jt808.domain.Body;
import com.avenger.jt808.domain.WritingMessageType;
import com.avenger.jt808.util.ByteArrayUtils;
import com.avenger.jt808.util.ByteBufUtils;
import com.avenger.jt808.util.LocalDateTimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by jg.wang on 2020/4/19.
 * Description:
 */
@WritingMessageType(type = (0x9202 - 0x10000))
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
        return ByteBufUtils.array(Unpooled.buffer(9)
                .writeByte(channelId)
                .writeByte(relayControl.ordinal())
                .writeByte(multiple)
                .writeBytes(ByteArrayUtils.bcdStrToBytes(LocalDateTimeUtils.formatTime(time, "yyMMddHHmmss"))));
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
